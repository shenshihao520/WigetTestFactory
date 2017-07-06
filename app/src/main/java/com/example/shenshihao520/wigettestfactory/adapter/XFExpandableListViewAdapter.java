package com.example.shenshihao520.wigettestfactory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.testData.InsurancePolicyChild;
import com.example.shenshihao520.wigettestfactory.testData.InsurancePolicyGroup;

import java.util.ArrayList;

/**
 * Created by shenshihao520 on 2017/6/16.
 * 可伸缩的listView 的adapter
 */

public class XFExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context  ;
    ArrayList<InsurancePolicyGroup> groups = new ArrayList<>();
    ArrayList<InsurancePolicyChild> childs = new ArrayList<>();
//    private int[][] childs_imgs = { { R.drawable.img1, R.drawable.img2 },
//            { R.drawable.img3, R.drawable.img4 , R.drawable.img5, R.drawable.img6},
//            { R.drawable.img7, R.drawable.img8 , R.drawable.img9}
//    };

    public XFExpandableListViewAdapter(Context context, ArrayList<InsurancePolicyGroup> groups, ArrayList<InsurancePolicyChild> childs){
        this.context = context;
        this.groups = groups;
        this.childs = childs;
    }
    //获取一级菜单的分组数目，比如这里就是3组："我的好友","同学","同事"
    public int getGroupCount() {
        return groups.size();
    }

    //获取每个一节菜单中二级菜单的分组数目,比如"家人"中有2个条目("老爸","老妈")
    public int getChildrenCount(int groupPosition) {
        return 1;

    }
    //获取每个一级菜单子项对象
    public InsurancePolicyGroup getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }
    //获取每个二级菜单子项对象
    public InsurancePolicyChild getChild(int groupPosition, int childPosition) {
        return childs.get(groupPosition);
    }

    //获取每个一级菜单子项对象Id
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //获取每个二级菜单子项对象Id
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    /**
     * hasStableIds有关于MyExpandablelistviewAdapter适配器刷新顺序
     * getGroupId和getChildId两个方法获取对象Id，获取到的Id,ExpandableListView会根据这个Id确定位置显示内容
     * 然而Id是否有效稳定是由hasStableIds决定的，也就是说：这个方法就是判断item的id是否稳定，
     * 如果有自己的id也就是true，那就是稳定，否则不稳定，则根据item位置来确定id
     *
     */
    public boolean hasStableIds() {
        return true;
    }

    static class GroupViewHolder {
        TextView tv_mainInsuranceName;
        TextView tv_insuranceNum;
        TextView tv_insurancedPeopleName1;
        TextView tv_insurancedPeopleName2;
        TextView tv_insurancedPeopleName3;
        TextView tv_coverage;
        TextView tv_premium;
        TextView tv_insurancedPolicyStatus;
        ImageView arrow;
    }
    static class ChildViewHolder {
        TextView tv_ipolicy_apply_date;
        TextView tv_ipolicy_effect_date;
        TextView tv_ipolicy_receipt_date;
        TextView tv_receipt_status;
        TextView tv_paymentPeriod;
        TextView tv_paymentWay;
        TextView tv_paymentEndDate;
        TextView tv_statusDeception;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.xf_third_insurance_search_group_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_mainInsuranceName = (TextView) convertView.findViewById(R.id.riskname);
            groupViewHolder.tv_insuranceNum = (TextView) convertView.findViewById(R.id.insuranceNum);
            groupViewHolder.tv_insurancedPeopleName1 = (TextView) convertView.findViewById(R.id.insurancedPeopleName1);
            groupViewHolder.tv_insurancedPeopleName2 = (TextView) convertView.findViewById(R.id.insurancedPeopleName2);
            groupViewHolder.tv_insurancedPeopleName3 = (TextView) convertView.findViewById(R.id.insurancedPeopleName3);
            groupViewHolder.tv_coverage = (TextView) convertView.findViewById(R.id.coverage);
            groupViewHolder.tv_premium = (TextView) convertView.findViewById(R.id.premium);
            groupViewHolder.tv_insurancedPolicyStatus = (TextView) convertView.findViewById(R.id.insurancedPolicyStatus);
            groupViewHolder.arrow = (ImageView) convertView.findViewById(R.id.arrow) ;
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        if(isExpanded)
        {
            groupViewHolder.arrow.setBackgroundResource(R.drawable.xfthirddownarraw);
        }
        else
        {
            groupViewHolder.arrow.setBackgroundResource(R.drawable.xfthirdrightarrow);
        }
        groupViewHolder.tv_mainInsuranceName.setText(groups.get(groupPosition).getMainInsuranceName());
        groupViewHolder.tv_insuranceNum.setText(groups.get(groupPosition).getInsuranceNum());
        groupViewHolder.tv_insurancedPeopleName1.setText(groups.get(groupPosition).getInsurancedPeopleName1());
        groupViewHolder.tv_insurancedPeopleName2.setText(groups.get(groupPosition).getInsurancedPeopleName2());
        groupViewHolder.tv_insurancedPeopleName3.setText(groups.get(groupPosition).getInsurancedPeopleName3());
        groupViewHolder.tv_coverage.setText(groups.get(groupPosition).getCoverage());
        groupViewHolder.tv_premium.setText(groups.get(groupPosition).getPremium());
        groupViewHolder.tv_insurancedPolicyStatus.setText(groups.get(groupPosition).getInsurancedPolicyStatus());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.xf_third_insurance_search_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder. tv_ipolicy_apply_date = (TextView) convertView.findViewById(R.id.ipolicy_apply_date_searched);
            childViewHolder. tv_ipolicy_effect_date = (TextView) convertView.findViewById(R.id.ipolicy_effect_date_searched);
            childViewHolder. tv_ipolicy_receipt_date = (TextView) convertView.findViewById(R.id.ipolicy_receipt_date_searched);
            childViewHolder. tv_receipt_status = (TextView) convertView.findViewById(R.id.receipt_status_searched);
            childViewHolder. tv_paymentPeriod = (TextView) convertView.findViewById(R.id.paymentPeriod_searched);
            childViewHolder. tv_paymentWay = (TextView) convertView.findViewById(R.id.paymentWay_searched);
            childViewHolder. tv_paymentEndDate = (TextView) convertView.findViewById(R.id.paymentEndDate_searched);
            childViewHolder. tv_statusDeception = (TextView) convertView.findViewById(R.id.statusDeception_searched);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tv_ipolicy_apply_date.setText(childs.get(groupPosition).getIpolicy_apply_date());
        childViewHolder.tv_ipolicy_effect_date.setText(childs.get(groupPosition).getIpolicy_effect_date());
        childViewHolder.tv_ipolicy_receipt_date.setText(childs.get(groupPosition).getIpolicy_receipt_date());
        childViewHolder.tv_receipt_status.setText(childs.get(groupPosition).getReceipt_status());
        childViewHolder.tv_paymentPeriod.setText(childs.get(groupPosition).getPaymentPeriod());
        childViewHolder.tv_paymentWay.setText(childs.get(groupPosition).getPaymentWay());
        childViewHolder.tv_paymentEndDate.setText(childs.get(groupPosition).getPaymentEndDate());
        childViewHolder.tv_statusDeception.setText(childs.get(groupPosition).getStatusDeception());


        //记得return convertView

        return convertView;
    }

    //true:让子项可选 ;     false:让子项不可选
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
