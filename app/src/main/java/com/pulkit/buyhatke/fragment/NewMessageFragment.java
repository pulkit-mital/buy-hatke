package com.pulkit.buyhatke.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.pulkit.buyhatke.R;
import com.pulkit.buyhatke.activity.MainActivity;
import com.pulkit.buyhatke.adapter.ThreadAdapter;
import com.pulkit.buyhatke.pojo.Message;
import com.pulkit.buyhatke.utils.DateUtils;
import com.pulkit.buyhatke.utils.Miscellaneous;

import static android.app.Activity.RESULT_OK;


/**
 * @author pulkitmital
 * @date 22-12-2016
 */

public class NewMessageFragment extends Fragment implements View.OnClickListener {


    RecyclerView mRecyclerView;
    EditText etMessage;
    ImageView ivSend, ivContact;
    AutoCompleteTextView actContact;
    ThreadAdapter mThreadAdapter;
    String address;
    private final int PICK_CONTACT = 1;

    public static NewMessageFragment newInstance() {
        Bundle arguments = new Bundle();
        NewMessageFragment fragment = new NewMessageFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {

        }

        @Override
        public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
        }

        @Override
        public void afterTextChanged(Editable pEditable) {
            if (pEditable.toString().equals("")) {
                ivSend.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_send_grey_24dp));
            } else {
                ivSend.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_send_accent_24dp));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_new_message, container, false);
        intializeView(lView);
        address = getArguments().getString("address");
        initRecyclerView();
        etMessage.addTextChangedListener(mWatcher);
        return lView;
    }

    private void intializeView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        ivSend = (ImageView) view.findViewById(R.id.ivSend);
        actContact = (AutoCompleteTextView) view.findViewById(R.id.actContact);
        ivContact = (ImageView) view.findViewById(R.id.ivContact);
        ivContact.setOnClickListener(this);
        ivSend.setOnClickListener(this);
    }

    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mThreadAdapter = new ThreadAdapter();
        mRecyclerView.setAdapter(mThreadAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getToolbar().setTitle("New Message");
        ActionBar lActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        lActionBar.setDisplayHomeAsUpEnabled(true);
        lActionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);

                if (c.moveToFirst()) {
                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    Toast.makeText(getContext(), "You've picked:" + name, Toast.LENGTH_LONG).show();
                    ContentResolver cr = getActivity().getContentResolver();
                    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                            "DISPLAY_NAME = '" + name + "'", null, null);
                    if (cursor.moveToFirst()) {
                        String contactId =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //
                        //  Get all phone numbers.
                        //
                        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext()) {
                            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            number = number.replaceAll("\\s", "");
                            number = number.replaceAll("-", "");
                            actContact.setText(Miscellaneous.removeCountryCode(number));
                        }
                        phones.close();
                    }
                    cursor.close();

                }


            }
        }
    }


    /**
     * Build ModelMessage
     *
     * @param message Body of message
     * @return
     */
    private Message buildMessage(String message) {
        Message message1 = new Message();
        message1.setType("2");
        message1.setAddress(address);
        message1.setBody(message);
        message1.setTimestamp(DateUtils.getCurrentTimeStamp());
        message1.setDate(DateUtils.getCurrentDate());
        return message1;
    }

    /**
     * Chnage conversation to initial view
     */
    private void restoreState() {
        etMessage.setText("");
        mRecyclerView.smoothScrollToPosition(mThreadAdapter.getMessageArrayList().size() - 1);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSend:
                String message = etMessage.getText().toString().trim();
                address = actContact.getText().toString().trim();
                if (address.equals("")) {
                    Toast.makeText(getContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                } else if (address.length() != 10) {
                    Toast.makeText(getContext(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                } else if (!message.equals("")) {
                    Message mMessage = buildMessage(message);

                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage(mMessage.getAddress(), null, mMessage.getBody(), null, null);

                    mThreadAdapter.updateData(mMessage);
                    restoreState();

                }
                break;

            case R.id.ivContact:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_CONTACT);
                }
                break;


        }
    }
}
