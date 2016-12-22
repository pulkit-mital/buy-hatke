package com.pulkit.buyhatke.fragment;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.pulkit.buyhatke.R;
import com.pulkit.buyhatke.activity.MainActivity;
import com.pulkit.buyhatke.adapter.ThreadAdapter;
import com.pulkit.buyhatke.pojo.Message;
import com.pulkit.buyhatke.utils.DateUtils;
import com.pulkit.buyhatke.utils.Singleton;

import java.util.ArrayList;

/**
 * @author pulkitmital
 * @date 22-12-2016
 */

public class ThreadFragment extends Fragment implements View.OnClickListener {


    private RecyclerView mRecyclerView;
    private EditText etMessage;
    private ImageView ivSend;

    private ThreadAdapter mThreadAdapter;
    private ArrayList<Message> messageArrayList;
    private String address;


    public static ThreadFragment newInstance(String address) {
        Bundle arguments = new Bundle();
        arguments.putString("address", address);
        ThreadFragment fragment = new ThreadFragment();
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
        View lView = inflater.inflate(R.layout.fragment_thread, container, false);
        initializeView(lView);
        address = getArguments().getString("address");
        messageArrayList = Singleton.getInstance().getMessageHashMap().get(address);
        initRecyclerView();
        etMessage.addTextChangedListener(mWatcher);
        mThreadAdapter.addData(messageArrayList);
        return lView;
    }


    private void initializeView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        ivSend = (ImageView) view.findViewById(R.id.ivSend);

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
        ((MainActivity) getActivity()).getToolbar().setTitle(address);
        ActionBar lActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        lActionBar.setDisplayHomeAsUpEnabled(true);
        lActionBar.setDisplayShowHomeEnabled(true);
    }

    /**
     * Build ModelMessage
     *
     * @param message Body of message
     * @return
     */
    private Message buildMessage(String message) {
        Message mMessage = new Message();
        mMessage.setType("2");
        mMessage.setAddress(address);
        mMessage.setBody(message);
        mMessage.setTimestamp(DateUtils.getCurrentTimeStamp());
        mMessage.setDate(DateUtils.getCurrentDate());
        return mMessage;
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
                if (!message.equals("")) {
                    Message mMessage = buildMessage(message);
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage(mMessage.getAddress(), null, mMessage.getBody(), null, null);
                    mThreadAdapter.updateData(mMessage);
                    restoreState();

                }
                break;
        }
    }
}
