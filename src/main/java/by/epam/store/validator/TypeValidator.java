package by.epam.store.validator;

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
}
