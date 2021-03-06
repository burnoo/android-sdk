package com.synerise.sdk.sample.ui.dev.apiAdapter.fragments.client;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.synerise.sdk.client.Client;
import com.synerise.sdk.core.listeners.DataActionListener;
import com.synerise.sdk.core.net.IApiCall;
import com.synerise.sdk.error.ApiError;
import com.synerise.sdk.sample.R;
import com.synerise.sdk.sample.ui.dev.BaseDevFragment;

public class ClientConfirmPhoneUpdateFragment extends BaseDevFragment {

    private IApiCall apiCall;
    private TextInputLayout inputPhone;
    private TextInputLayout inputCode;
    private CheckBox smsAgreement, enableAgreement;

    public static ClientConfirmPhoneUpdateFragment newInstance() {
        return new ClientConfirmPhoneUpdateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_confirm_phone_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputPhone = view.findViewById(R.id.input_phone);
        inputCode = view.findViewById(R.id.input_code);
        smsAgreement = view.findViewById(R.id.sms_agreement);
        enableAgreement = view.findViewById(R.id.enable_agreement);
        enableAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                smsAgreement.setAlpha(0.5f);
                smsAgreement.setEnabled(false);
            } else {
                smsAgreement.setAlpha(1f);
                smsAgreement.setEnabled(true);
            }
        });
        view.findViewById(R.id.phone_update).setOnClickListener(v -> confirmPhoneUpdate());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (apiCall != null) apiCall.cancel();
    }

    @SuppressWarnings("ConstantConditions")
    private void confirmPhoneUpdate() {

        inputCode.setError(null);

        boolean isValid = true;

        String phone = inputPhone.getEditText().getText().toString();
        String code = inputCode.getEditText().getText().toString();

        if (TextUtils.isEmpty(code)) {
            isValid = false;
            inputCode.setError(getString(R.string.error_empty));
        }

        if (isValid) {
            if (apiCall != null) apiCall.cancel();
            apiCall = Client.confirmPhoneUpdate(phone, code, enableAgreement.isChecked() ? null : smsAgreement.isChecked());
            apiCall.execute(this::onSuccess, new DataActionListener<ApiError>() {
                @Override
                public void onDataAction(ApiError apiError) {
                    onFailure(apiError);
                }
            });
        }
    }
}