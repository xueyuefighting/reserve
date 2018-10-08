package com.ceo.family.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 	对象Copy数据
 * 	说明：
 * 	   1、兼容的时间戳转换long类型的相互转换方式.
 * 	   2、Integer、Long、Double转换为空时赋值不成功的情况，如果源对象为空，赋值默认为0.
 * @author: 化阵
 * @date: 2018/06/01
 */
public  class BeanCopy extends BeanUtils {

    private static final String timeStampName = Timestamp.class.getSimpleName();

    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, (Class) null, (String[]) null);
    }

    /**
     * 拷贝DTO到DO中
     *
     * @param
     * @return
     * @author zhangjinliang
     * @date 2018/8/1 下午8:34
     */
    public static void copyProperties(Object source, Object target, boolean flag) throws BeansException {
        copyProperties(source, target, (Class) null, getNullPropertyNames(source));
    }

    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }

            actualEditable = editable;
        }

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        PropertyDescriptor[] var7 = targetPds;
        int var8 = targetPds.length;
        for(int var9 = 0; var9 < var8; ++var9) {
            PropertyDescriptor targetPd = var7[var9];
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    Class sourceClazz = writeMethod.getParameterTypes()[0];
                    Class targetClazz = readMethod.getReturnType();
                    String sourceClassName = sourceClazz.getSimpleName();
                    String targetClassName = targetClazz.getSimpleName();
                    if (readMethod != null &&
                            (ClassUtils.isAssignable(sourceClazz, targetClazz)
                                //DO-DTO
                                || (timeStampName.equals(sourceClassName) && "long".equals(targetClassName))
                                //DTO->DO
                                || ("long".equals(sourceClassName) && timeStampName.equals(targetClassName))
                            )) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object tempValue = readMethod.invoke(source);
                            Object value = tempValue;

                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }

                            // 兼容DO -> DTO的空指针异常
                            if(timeStampName.equals(sourceClassName) && "long".equals(targetClassName)){
                                value = DateUtils.transferLongToTimestamp(Long.valueOf(tempValue.toString()));
                            }else if(Integer.class.getSimpleName().equals(targetClassName) && tempValue == null){
                                value = Integer.valueOf(0);
                            }else if(Long.class.getSimpleName().equals(targetClassName) && tempValue == null){
                                value = Long.valueOf(0);
                            }else if(Double.class.getSimpleName().equals(targetClassName) && tempValue == null){
                                value = Double.valueOf(0);
                            // 兼容DO -> DTO的空指针异常
                            }if("long".equals(sourceClassName) && timeStampName.equals(targetClassName)){
                                if(tempValue == null){
                                    tempValue = DateUtils. transferTimestampToLong( new Timestamp(new Date().getTime()));
                                }
                                value = DateUtils.transferTimestampToLong((Timestamp)tempValue);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable var15) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var15);
                        }
                    }
                }
            }
        }

    }

    /**
     * 增加过滤
     *
     * @param
     * @return
     * @author zhangjinliang
     * @date 2018/8/1 下午8:34
     */
    public static String[] getNullPropertyNames(Object source) {

//        long count = Stream.of(source.getClass().getInterfaces()).filter(x -> TBase.class.getSimpleName().equals(x.getSimpleName())).count();
//        if(count<=0) throw new IllegalArgumentException(" source is not thrift class");

        final String keyword = "get";
        Set<String> nonSetValue = Stream.of(source.getClass().getDeclaredMethods())
                .filter(x -> x.getName().startsWith(keyword) && x.getName().length() > keyword.length())
                .filter(x -> {
                    try {
                        return !(boolean) x.invoke(source, null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return false;
                }).map(x -> x.getName().substring(keyword.length()))
                .map(x -> lowerFirst(x)).collect(Collectors.toSet());

        String[] result = new String[nonSetValue.size()];
        return nonSetValue.toArray(result);
    }

    /**
     * 首字母小写
     *
     * @param
     * @return
     * @author zhangjinliang
     * @date 2018/8/1 下午8:34
     */
    public static String lowerFirst(String oldStr) {

        char[] chars = oldStr.toCharArray();

        chars[0] += 32;

        return String.valueOf(chars);

    }
//
//    public static <T> List<T> transferDOsToDTOs(List<?> dos, Class<T> dtoClass) throws Exception{
//        long count = Stream.of(dtoClass.getInterfaces()).filter(x -> TBase.class.getSimpleName().equals(x.getSimpleName())).count();
//        if(count<=0) throw new IllegalArgumentException(" dtoClass is not thrift class");
//        if(dos!=null && dos.size()>0){
//            T dto = null;
//            List<T> t = new ArrayList<>();
//            for(Object ds : dos){
//                dto = dtoClass.newInstance();
//                BeanCopy.copyProperties(ds,dto);
//                t.add(dto);
//            }
//            return t;
//        }
//        return null;
//    }
//    public static<T> T transferDOToDTO(Object dos, Class<T> dtoClass) throws Exception{
//        long count = Stream.of(dtoClass.getInterfaces()).filter(x -> TBase.class.getSimpleName().equals(x.getSimpleName())).count();
//        if(count<=0) throw new IllegalArgumentException(" dtoClass is not thrift class");
//        if(dos==null) throw new IllegalArgumentException(" dtoClass must not be null");
//        T dto = null;
//        dto = dtoClass.newInstance();
//        BeanCopy.copyProperties(dos,dto);
//        return dto;
//    }
}