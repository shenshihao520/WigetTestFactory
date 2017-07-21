package com.example.shenshihao520.wigettestfactory.testActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.testData.InsurancePolicyChild;
import com.example.shenshihao520.wigettestfactory.testData.InsurancePolicyGroup;
import com.example.shenshihao520.wigettestfactory.adapter.XFExpandableListViewAdapter;
import com.example.shenshihao520.wigettestfactory.widget.spiner.SpinerButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * 可扩展的ListView
 *
 * Created by shenshihao520 on 2017/6/17.
 */

public class ExpandableListViewActivity extends Activity  {

    private List<String> nameList = new ArrayList<String>();
    SpinerButton ipolicy_status;
    SpinerButton ipolicy_receipt_status;
    private ExpandableListView expan_listview;

    ArrayList<InsurancePolicyGroup> groups = new ArrayList<>();
    ArrayList<InsurancePolicyChild> childs = new ArrayList<>();

    private Calendar showDate;

    TextView tv_ipolicy_apply_date_from;
    TextView tv_ipolicy_apply_date_end;
    TextView tv_ipolicy_effect_date_from;
    TextView tv_ipolicy_effect_date_end;
    TextView tv_ipolicy_receipt_date_from;
    TextView tv_ipolicy_receipt_date_end;

    RelativeLayout rl_ipolicy_apply_date_from;
    RelativeLayout rl_ipolicy_apply_date_end;
    RelativeLayout rl_ipolicy_effect_date_from;
    RelativeLayout rl_ipolicy_effect_date_end;
    RelativeLayout rl_ipolicy_receipt_date_from;
    RelativeLayout rl_ipolicy_receipt_date_end;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xf_third_insurance_search);
        initView();
        initData();
    }
    void initView(){
        showDate=Calendar.getInstance();
        tv_ipolicy_apply_date_from = (TextView)findViewById(R.id.ipolicy_apply_date_from);
        tv_ipolicy_apply_date_end = (TextView)findViewById(R.id.ipolicy_apply_date_end);
        tv_ipolicy_effect_date_from = (TextView)findViewById(R.id.ipolicy_effect_date_from);
        tv_ipolicy_effect_date_end = (TextView)findViewById(R.id.ipolicy_effect_date_end);
        tv_ipolicy_receipt_date_from = (TextView)findViewById(R.id.ipolicy_receipt_date_from);
        tv_ipolicy_receipt_date_end = (TextView)findViewById(R.id.ipolicy_receipt_date_end);

        rl_ipolicy_apply_date_from = (RelativeLayout)findViewById(R.id.rl_ipolicy_apply_date_from);
        rl_ipolicy_apply_date_end = (RelativeLayout)findViewById(R.id.rl_ipolicy_apply_date_end);
        rl_ipolicy_effect_date_from = (RelativeLayout)findViewById(R.id.rl_ipolicy_effect_date_from);
        rl_ipolicy_effect_date_end = (RelativeLayout)findViewById(R.id.rl_ipolicy_effect_date_end);
        rl_ipolicy_receipt_date_from = (RelativeLayout)findViewById(R.id.rl_ipolicy_receipt_date_from);
        rl_ipolicy_receipt_date_end = (RelativeLayout)findViewById(R.id.rl_ipolicy_receipt_date_end);

        rl_ipolicy_apply_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tv_ipolicy_apply_date_from);
            }
        });
        rl_ipolicy_apply_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tv_ipolicy_apply_date_end);
            }
        });
        rl_ipolicy_effect_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tv_ipolicy_effect_date_from);
            }
        });
        rl_ipolicy_effect_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tv_ipolicy_effect_date_end);
            }
        });
        rl_ipolicy_receipt_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tv_ipolicy_receipt_date_from);
            }
        });
        rl_ipolicy_receipt_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tv_ipolicy_receipt_date_end);
            }
        });
        ipolicy_status = (SpinerButton)findViewById(R.id.ipolicy_status);
        ipolicy_receipt_status = (SpinerButton)findViewById(R.id.ipolicy_receipt_status);
        expan_listview = (ExpandableListView) findViewById(R.id.expandablelv);
        expan_listview.setGroupIndicator(null);
        expan_listview.setDivider(null);

        expan_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });
        expan_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });
    }

    void initData(){
        getData();
        String[] names = getResources().getStringArray(R.array.hero_name);
        for(int i = 0; i < names.length-1; i++){
            nameList.add(names[i]);
        }
        ipolicy_status.setListData(nameList);
        ipolicy_receipt_status.setListData(nameList);
        expan_listview.setAdapter(new XFExpandableListViewAdapter(this,groups,childs));
    }
    void getData()
    {
        InsurancePolicyGroup insurancePolicyGroup = new InsurancePolicyGroup();
        insurancePolicyGroup.setMainInsuranceName("sadf");
        insurancePolicyGroup.setInsuranceNum("asd");
        insurancePolicyGroup.setCoverage("asd");
        insurancePolicyGroup.setInsurancedPeopleName1("asd");
        insurancePolicyGroup.setInsurancedPeopleName2("asd");
        insurancePolicyGroup.setInsurancedPeopleName3("asd");
        insurancePolicyGroup.setInsurancedPolicyStatus("asd");
        insurancePolicyGroup.setPremium("asd");
        groups.add(insurancePolicyGroup);
        groups.add(insurancePolicyGroup);

        InsurancePolicyChild insurancePolicyChild = new InsurancePolicyChild();
        insurancePolicyChild.setIpolicy_apply_date("123");
        insurancePolicyChild.setIpolicy_effect_date("123");
        insurancePolicyChild.setIpolicy_receipt_date("123");
        insurancePolicyChild.setPaymentEndDate("123");
        insurancePolicyChild.setPaymentPeriod("123");
        insurancePolicyChild.setPaymentWay("123");
        insurancePolicyChild.setReceipt_status("123");
        insurancePolicyChild.setStatusDeception("123");
        childs.add(insurancePolicyChild);
        childs.add(insurancePolicyChild);

    }
    private void showDateDialog(final TextView textView) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showDate.set(Calendar.YEAR,year);
                showDate.set(Calendar.MONTH,monthOfYear);
                showDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                textView.setText(DateFormat.format("yyyy-MM-dd", showDate));
            }
        }, showDate.get(Calendar.YEAR), showDate.get(Calendar.MONTH), showDate.get(Calendar.DAY_OF_MONTH)).show();
    }


}