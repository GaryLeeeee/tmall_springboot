package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.ProductMapper;
import com.garylee.tmall_springboot.dao.PropertyMapper;
import com.garylee.tmall_springboot.dao.PropertyValueMapper;
import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.domain.Property;
import com.garylee.tmall_springboot.domain.PropertyValue;
import com.garylee.tmall_springboot.domain.PropertyValueExample;
import com.garylee.tmall_springboot.service.PropertyService;
import com.garylee.tmall_springboot.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-27 20:03.
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService{
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;
    @Override
    public void init(Product product) {
        //一个分类里的所有产品拥有相同的属性(值不同)
        List<Property> properties = propertyService.list(product.getCid());
        for(Property property:properties){
            int pid = product.getId();
            int ptid = property.getId();
            PropertyValue propertyValue = get(pid,ptid);
            if(null==propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setPid(pid);
                propertyValue.setPtid(ptid);
                propertyValueMapper.insert(propertyValue);
            }
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public PropertyValue get(int pid, int ptid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
        List<PropertyValue> propertyValue = propertyValueMapper.selectByExample(propertyValueExample);
        if(propertyValue.isEmpty())
            return null;
        return propertyValue.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
        for(PropertyValue propertyValue:propertyValues){
            //设置propertyValue的非数据库字段property
            propertyValue.setProperty(propertyService.get(propertyValue.getPtid()));
        }
        return propertyValues;
    }
}
