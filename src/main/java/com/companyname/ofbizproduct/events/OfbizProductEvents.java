package com.companyname.ofbizproduct.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import java.text.DecimalFormat;

public class OfbizProductEvents {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static final String module = OfbizProductEvents.class.getName();

    public static String createOfbizProductEvent(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        double dPrice = Double.parseDouble(price);
        double dTax = dPrice * 0.18;
        String tax = String.valueOf(df.format(dTax));

        if (UtilValidate.isEmpty(name) || UtilValidate.isEmpty(category) || UtilValidate.isEmpty(price)) {
            String errMsg = "Name, Category, Price an are required fields on the form and can't be empty.";
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        try {
            Debug.logInfo("=======Creating OfbizProduct record in event using service createOfbizProductByJavaService=========", module);
            dispatcher.runSync("createOfbizProductByJavaService", UtilMisc.toMap("name", name,
                    "category", category, "price", price, "tax", tax, "userLogin", userLogin));
        } catch (GenericServiceException e) {
            String errMsg = "Unable to create new records in OfbizProduct entity: " + e.toString();
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        request.setAttribute("_EVENT_MESSAGE_", "OFBiz Product created successfully.");
        return "success";
    }
}