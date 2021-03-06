package com.synerise.sdk.sample.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AlertDialog;

import com.synerise.sdk.error.ApiError;
import com.synerise.sdk.error.ApiErrorBody;
import com.synerise.sdk.error.ApiErrorCause;
import com.synerise.sdk.sample.R;

import java.util.List;

import static com.synerise.sdk.error.HttpErrorCategory.UNAUTHORIZED;

public class BaseFragment extends Fragment {

    protected interface Args {
        String CONTENT = "content";
    }

    protected void showAlertError(ApiError apiError) {
        FragmentActivity context = getActivity();
        if (context != null && apiError != null) {
            apiError.printStackTrace();
            ApiErrorBody errorBody = apiError.getErrorBody();
            int httpCode = apiError.getHttpCode();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context).setIcon(R.drawable.sygnet_synerise);
            if (httpCode != ApiError.UNKNOWN_CODE) {
                dialog.setTitle(String.valueOf(httpCode));
            } else {
                dialog.setTitle(R.string.default_error);
            }
            if (errorBody != null) {
                List<ApiErrorCause> errorCauses = errorBody.getErrorCauses();
                StringBuilder message = new StringBuilder(errorBody.getMessage());
                if (!errorCauses.isEmpty()) {
                    for (ApiErrorCause errorCause : errorCauses) {
                        message.append("\n").append(errorCause.getMessage());
                    }
                }
                dialog.setMessage(message.toString());
            } else {
                switch (apiError.getErrorType()) {
                    case HTTP_ERROR:
                        if (apiError.getHttpErrorCategory() == UNAUTHORIZED) {
                            dialog.setMessage(getString(R.string.error_unauthorized));
                        } else {
                            dialog.setMessage(getString(R.string.error_http));
                        }
                        break;
                    case NETWORK_ERROR:
                        dialog.setMessage(getString(R.string.error_network));
                        break;
                    case NO_TOKEN:
                        dialog.setMessage(getString(R.string.no_token));
                        break;
                    default:
                        dialog.setMessage(getString(R.string.error_default));
                }
            }
            dialog.show();
        }
    }
}