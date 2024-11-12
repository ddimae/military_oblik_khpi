package ntukhpi.semit.militaryoblik.adapters;

import java.lang.reflect.Field;
import java.util.Collection;

public interface IBaseAdapter {
    public default void merge(IBaseAdapter target, boolean recursive) {
        try {
            Field[] fields = target.getClass().getDeclaredFields();

            for (Field targetField : fields) {
                Field sourceField = this.getClass().getDeclaredField(targetField.getName());
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                Object targetValue = targetField.get(target);
                Object sourceValue = sourceField.get(this);

//                System.out.println(targetField.getName() + ":\t" + targetValue + "\t->\t" + sourceValue);

                if (targetValue == null)
                    continue;
                if (targetValue instanceof String && ((String) targetValue).isBlank())
                    continue;
                if (targetValue instanceof Collection<?> && ((Collection<?>) targetValue).isEmpty())
                    continue;
                if (targetValue instanceof IBaseAdapter && recursive) {
                    ((IBaseAdapter) sourceValue).merge((IBaseAdapter) targetValue, true);
                    continue;
                }

                sourceField.set(this, targetValue);
            }

//            for (Field sourceField : fields) {
//                sourceField.setAccessible(true);
//                Object sourceValue = sourceField.get(this);
//
//                System.out.println(sourceField.getName() + ":\t" + sourceValue);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
