package com.companyname.ofbizproduct.services;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

public class OfbizProductServices {

    public static final String module = OfbizProductServices.class.getName();

    public static Map<String, Object> createOfbizProduct(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dctx.getDelegator();
        try {
            GenericValue ofbizProduct = delegator.makeValue("OfbizProduct");
            // Auto generating next sequence of ofbizProductId primary key
            ofbizProduct.setNextSeqId();
            // Setting up all non primary key field values from context map
            ofbizProduct.setNonPKFields(context);
            // Creating record in database for OfbizProduct entity for prepared value
            ofbizProduct = delegator.create(ofbizProduct);
            result.put("ofbizProductId", ofbizProduct.getString("ofbizProductId"));
            Debug.log("==========This is my first Java Service implementation in Apache OFBiz. OfbizProduct record created successfully with ofbizProductId: " + ofbizProduct.getString("ofbizProductId"));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError("Error in creating record in OfbizProduct entity ........" +module);
        }
        return result;
    }
}