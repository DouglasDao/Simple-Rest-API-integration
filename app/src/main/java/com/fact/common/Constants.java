package com.fact.common;

public interface Constants {


    interface BundleKey {

    }

    interface InternalHttpCode {
        int SUCCESS_CODE = 200;
        int FAILURE_CODE = 204;
        int UNAUTH_CODE = 401;
        int SERVER_ERROR = 500;
    }

    interface HttpErrorMessage {
        String INTERNAL_SERVER_ERROR = "server maintance error";
    }
}
