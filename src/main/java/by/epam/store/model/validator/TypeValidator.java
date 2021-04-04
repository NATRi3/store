package by.epam.store.model.validator;

import by.epam.store.model.entity.TypeRole;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.model.service.TypeSort;

import java.util.Set;

/**
 * The type Type validator.
 */
public class TypeValidator {
    private static final Set<String> typeProductSort = Set.of(TypeSort.PRICE.toString(),
            TypeSort.NAME.toString(), TypeSort.PRICEDESC.toString(), TypeSort.NAMEDESC.toString());
    private static final Set<String> typeStatus = Set.of(TypeStatus.ACTIVE.toString(), TypeStatus.NONACTIVE.toString(),
            TypeStatus.BLOCKED.toString());
    private static final Set<String> typeNewsSort = Set.of(TypeSort.DATE.toString(), TypeSort.DATEDESC.toString(),
            TypeSort.TITLE.toString(), TypeSort.TITLEDESC.toString());
    private static final Set<String> typeRole = Set.of(TypeRole.CLIENT.toString(), TypeRole.ADMIN.toString(),
            TypeRole.GUEST.toString());
    private static final Set<String> typeCollectionStatus = Set.of(TypeStatus.ACTIVE.toString(),
            TypeStatus.BLOCKED.toString());
    private static final Set<String> typeOrderStatus = Set.of(TypeStatus.WAIT.toString(), TypeStatus.DECLINE.toString(),
            TypeStatus.COMPLETED.toString(), TypeStatus.INPROGRESS.toString());
    private static final Set<String> typeOrderSort = Set.of(TypeSort.DATE.toString(), TypeSort.PRICE.toString(),
            TypeSort.DATEDESC.toString(), TypeSort.PRICEDESC.toString());

    /**
     * Is type product sort boolean.
     *
     * @param sort the sort
     * @return the boolean
     */
    public static boolean isTypeProductSort(String sort) {
        if (sort == null) return false;
        return typeProductSort.contains(sort);
    }

    /**
     * Is type status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    public static boolean isTypeStatus(String status) {
        if (status == null) return false;
        return typeStatus.contains(status.toUpperCase());
    }

    /**
     * Is type news sort boolean.
     *
     * @param sort the sort
     * @return the boolean
     */
    public static boolean isTypeNewsSort(String sort) {
        if (sort == null) return false;
        return typeNewsSort.contains(sort);
    }

    /**
     * Is type role boolean.
     *
     * @param role the role
     * @return the boolean
     */
    public static boolean isTypeRole(String role) {
        if (role == null) return false;
        return typeRole.contains(role);
    }

    /**
     * Is type collection status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    public static boolean isTypeCollectionStatus(String status) {
        if (status == null) return false;
        return typeCollectionStatus.contains(status.toUpperCase());
    }

    /**
     * Is type order sort boolean.
     *
     * @param sort the sort
     * @return the boolean
     */
    public static boolean isTypeOrderSort(String sort) {
        if (sort == null) return false;
        return typeOrderSort.contains(sort);
    }

    /**
     * Is type order status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    public static boolean isTypeOrderStatus(String status) {
        if (status == null) return false;
        return typeOrderStatus.contains(status);
    }
}
