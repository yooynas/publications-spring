package com.carlospaelinck.security

import com.carlospaelinck.domain.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.core.Authentication

class PublicationsMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject
    private Object returnObject
    private Object target

    public PublicationsMethodSecurityExpressionRoot(Authentication a) {
        super(a)
    }

    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject
    }

    public Object getFilterObject() {
        return filterObject
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject
    }

    public Object getReturnObject() {
        return returnObject
    }

    void setThis(Object target) {
        this.target = target
    }

    public Object getThis() {
        return target
    }

    // Add your own evaluators here:

    public boolean isOwner(Object object) {
        def principal = (PublicationsUserDetails) this.getPrincipal();

        // Check document owner
        if (object instanceof Document) {
            Document document = (Document) object
            return document.getUser().getId() == principal.getUser().getId()
        }

        // Otherwise, nothing matched so fail for safety
        return false
    }

}
