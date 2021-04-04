package by.epam.store.model.service.impl;

import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.model.dao.impl.BaseUserDao;
import by.epam.store.model.entity.TypeRole;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.model.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * The type Base user service test.
 */
public class BaseUserServiceTest {
    private static final Logger log = LogManager.getLogger(BaseUserServiceTest.class);
    private UserService service;
    private BaseUserDao dao;
    private User expected;
    private List<User> expectedList;
    private String password;
    private Map<String, String[]> params;

    /**
     * Sets up.
     */
    @BeforeMethod
    public void setUp() {
        dao = Mockito.mock(BaseUserDao.class);
        service = new BaseUserService(dao);
        expected = User.builder()
                .id(1)
                .name("TEST")
                .email("ssykhorukov@gmail.com")
                .registerDate(new Date())
                .role(TypeRole.ADMIN)
                .access(TypeStatus.ACTIVE)
                .imageName("test")
                .build();
        expectedList = List.of(expected);
        password = "123";
        params = new HashMap<>();
        params.put(RequestParameterAndAttribute.PASSWORD, new String[]{password});
        params.put(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD, new String[]{password});
        params.put(RequestParameterAndAttribute.ID_USER, new String[]{String.valueOf(expected.getId())});
        params.put(RequestParameterAndAttribute.EMAIL, new String[]{expected.getEmail()});
    }

    /**
     * Test activate.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testActivate() throws DaoException, ServiceException {
        Mockito.when(dao.changeStatus(Mockito.anyLong(), Mockito.anyObject(), Mockito.anyObject())).thenReturn(true);
        assertEquals(service.activate("1"), MessageKey.SUCCESSFUL_ACTIVATION);
    }

    /**
     * Test login.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testLogin() throws DaoException, ServiceException {
        Mockito.when(dao.findEntityByEmailAndPassword(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(Optional.of(User.builder().access(TypeStatus.ACTIVE).build()));
        assertFalse(service.login(expected, "123").isPresent());
    }

    /**
     * Test login blocked.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testLoginBlocked() throws DaoException, ServiceException {
        Mockito.when(dao.findEntityByEmailAndPassword(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(Optional.of(User.builder().access(TypeStatus.BLOCKED).build()));
        assertEquals(service.login(expected, ""), Optional.of(MessageKey.ERROR_MESSAGE_USER_BLOCKED));
    }

    /**
     * Test login nonactive.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testLoginNonactive() throws DaoException, ServiceException {
        Mockito.when(dao.findEntityByEmailAndPassword(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(Optional.of(User.builder().access(TypeStatus.NONACTIVE).build()));
        assertEquals(service.login(expected, ""), Optional.of(MessageKey.ERROR_MESSAGE_USER_NONACTIVE));
    }

    /**
     * Test login not found.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testLoginNotFound() throws DaoException, ServiceException {
        Mockito.when(dao.findEntityByEmailAndPassword(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(Optional.empty());
        assertEquals(service.login(expected, ""), Optional.of(MessageKey.ERROR_MESSAGE_WRONG_EMAIL_OR_PASS));
    }

    /**
     * Test register client.
     *
     * @throws ServiceException the service exception
     * @throws DaoException     the dao exception
     */
    @Test
    public void testRegisterClient() throws ServiceException, DaoException {
        Mockito.when(dao.createUser(Mockito.any(User.class), Mockito.anyString())).thenReturn(expected);
        Mockito.when(dao.isEmailExists(Mockito.anyObject()))
                .thenReturn(false);
        assertEquals(service.registerClient(params), Optional.empty());
    }

    /**
     * Test register client exists.
     *
     * @throws ServiceException the service exception
     * @throws DaoException     the dao exception
     */
    @Test
    public void testRegisterClientExists() throws ServiceException, DaoException {
        Mockito.when(dao.createUser(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(expected);
        Mockito.when(dao.isEmailExists(Mockito.anyObject()))
                .thenReturn(true);
        Map<String, String[]> params = new HashMap<>();
        params.put(RequestParameterAndAttribute.PASSWORD, new String[]{password});
        assertEquals(service.registerClient(params), Optional.of(MessageKey.ERROR_MESSAGE_EMAIL_EXIST));
    }

    /**
     * Test find user by id.
     *
     * @throws ServiceException the service exception
     * @throws DaoException     the dao exception
     */
    @Test
    public void testFindUserById() throws ServiceException, DaoException {
        Mockito.when(dao.findEntityById(Mockito.anyObject()))
                .thenReturn(Optional.ofNullable(expected));
        assertEquals(service.findUserById(1), Optional.of(expected));
    }

    /**
     * Test update by id.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testUpdateById() throws DaoException, ServiceException {
        Mockito.when(dao.update(Mockito.anyObject()))
                .thenReturn(true);
        assertEquals(service.updateById(expected), true);
    }

    /**
     * Test change password send forgot mail message.
     *
     * @throws ServiceException the service exception
     * @throws DaoException     the dao exception
     */
    @Test
    public void testChangePasswordSendForgotMailMessage() throws ServiceException, DaoException {
        Mockito.when(dao.findUserByEmail(expected.getEmail()))
                .thenReturn(Optional.ofNullable(expected));
        assertEquals(service.changePasswordSendForgotMailMessage(expected.getEmail()), Optional.empty());
    }

    /**
     * Test find users by role and status.
     *
     * @throws ServiceException the service exception
     * @throws DaoException     the dao exception
     */
    @Test
    public void testFindUsersByRoleAndStatus() throws ServiceException, DaoException {
        Mockito.when(dao.findUserByRoleAndStatus(expected.getAccess(), 1))
                .thenReturn(expectedList);
        assertEquals(service.findUsersByRoleAndStatus(expected.getAccess().toString(), "1"), expectedList);
    }

    /**
     * Test change status from to.
     *
     * @throws ServiceException the service exception
     * @throws DaoException     the dao exception
     */
    @Test
    public void testChangeStatusFromTo() throws ServiceException, DaoException {
        Mockito
                .when(dao.changeStatus(
                        expected.getId(),
                        expected.getAccess(),
                        expected.getAccess()
                        )
                ).thenReturn(true);
        assertEquals(
                MessageKey.SUCCESSFUL_CHANGE,
                service.changeStatusFromTo(
                        String.valueOf(expected.getId()),
                        expected.getAccess().name(),
                        expected.getAccess().name()
                )
        );
    }

    /**
     * Test register admin.
     *
     * @throws ServiceException the service exception
     * @throws DaoException     the dao exception
     */
    @Test
    public void testRegisterAdmin() throws ServiceException, DaoException {
        Mockito.when(dao.createUser(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(expected);
        Mockito.when(dao.isEmailExists(expected.getEmail()))
                .thenReturn(false);
        assertFalse(service.registerAdmin(params).isPresent());
    }

    /**
     * Test change password.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testChangePassword() throws DaoException, ServiceException {
        Mockito
                .when(dao.changePassword(
                        expected.getId(),
                        password,
                        password
                        )
                ).thenReturn(true);
        assertEquals(service.changePassword(params), MessageKey.SUCCESSFUL_CHANGE);
    }

    /**
     * Test change image.
     *
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test
    public void testChangeImage() throws DaoException, ServiceException {
        Mockito
                .when(dao.changeImageById(
                        expected.getId(),
                        expected.getImageName()
                        )
                ).thenReturn(true);

        assertEquals(
                MessageKey.SUCCESSFUL_CHANGE,
                service.changeImage(
                        String.valueOf(expected.getId()),
                        expected.getImageName()
                )
        );
    }
}