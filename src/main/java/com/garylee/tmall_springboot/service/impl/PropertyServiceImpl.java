package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.domain.Property;
import com.garylee.tmall_springboot.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyServiceImpl implements PropertyService{
    @Autowired
    PropertyService propertyService;
    @Override
    public void add(Property property) {
        propertyService.add(property);
    }

    @Override
    public void delete(int id) {
        propertyService.delete(id);
    }

    @Override
    public void update(Property property) {
        propertyService.update(property);
    }

    @Override
    public Property get(int id) {
        return propertyService.get(id);
    }

    @Override
    public List<Property> list(int id) {
        return propertyService.list(id);
    }
}
