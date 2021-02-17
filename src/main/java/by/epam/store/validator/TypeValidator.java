package by.epam.store.validator;

import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.service.TypeSort;

import java.util.Set;

public class TypeValidator {
    private static final Set<String> typeProductSort = Set.of(TypeSort.PRICE.toString(),
            TypeSort.NAME.toString(), TypeSort.PRICEDECS.toString(),TypeSort.NAMEDECS.toString());
    private static final Set<String> typeStatus = Set.of(TypeStatus.ACTIVE.toString(),TypeStatus.NONACTIVE.toString(),
            TypeStatus.BLOCKED.toString());
    private static final Set<String> typeNewsSort = Set.of(TypeSort.DATE.toString(),TypeSort.DATEDESC.toString(),
            TypeSort.TITLE.toString(),TypeSort.TITLEDESC.toString());
    private static final Set<String> typeRole = Set.of(TypeRole.CLIENT.toString(),TypeRole.ADMIN.toString(),
            TypeRole.GUEST.toString(),TypeRole.MANAGER.toString());
    private static Set<String> typeCollectionStatus = Set.of(TypeStatus.ACTIVE.toString(),
            TypeStatus.BLOCKED.toString());

    public static boolean isTypeProductSort(String sort){
        if(sort==null)return false;
        return typeProductSort.contains(sort);
    }
    public static boolean isTypeStatus(String status){
        if(status==null)return false;
        return typeStatus.contains(status.toUpperCase());
    }
    public static boolean isTypeNewsSort(String sort){
        if(sort==null)return false;
        return typeNewsSort.contains(sort);
    }

    public static boolean isTypeRole(String role) {
        if(role==null) return false;
        return typeRole.contains(role);
    }

    public static boolean isTypeCollectionStatus(String status) {
        if(status==null)return false;
        return typeCollectionStatus.contains(status.toUpperCase());
    }
}
