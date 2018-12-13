package com.learn.Utils;

import com.learn.Utils.reflection.BeanUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * 作用:用来实体对象中的null赋值
 */
public class JpaUpdateUtil {
    /**
     * 将空值的属性从目标实体类中复制到源实体类中
     * @param src : 要将属性中的空值覆盖的对象(源实体类)
     * @param target :从数据库根据id查询出来的目标对象
     */
    public static void copyNonNullProperties(Object  src,Object target) throws NoSuchFieldException, IllegalAccessException {
//        BeanUtils.copyProperties(src,target,getNullProperties(src));
        BeanUtil.copyBean(src, target,BeanUtil.getNullValueFields(src));
        System.out.println();
    }

    /**
     * 将为空的properties给找出来,然后返回出来
     * @param src
     * @return
     */
    private static String[] getNullProperties(Object src){
        BeanWrapper srcBean=new BeanWrapperImpl(src);
        PropertyDescriptor[] pds=srcBean.getPropertyDescriptors();
        Set<String> emptyName=new HashSet<>();
        for(PropertyDescriptor p:pds){
            Object srcValue=srcBean.getPropertyValue(p.getName());
            if(srcValue==null) emptyName.add(p.getName());
        }
        String[] result=new String[emptyName.size()];
        return emptyName.toArray(result);
    }
}