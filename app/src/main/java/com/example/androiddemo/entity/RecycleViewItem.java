package com.example.androiddemo.entity;



import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
//import androidx.databinding.library.baseAdapters.BR;

//import com.example.androiddemo.BR;


public class RecycleViewItem extends BaseObservable {
    private String name;
    private int age;
    private float amount;
    private String detail;

    public RecycleViewItem(String name, int age, float amount, String detail) {
        this.name = name;
        this.age = age;
        this.amount = amount;
        this.detail = detail;
    }

    public RecycleViewItem(){

    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public int getAge() {
        return age;
    }

    @Bindable
    public float getAmount() {
        return amount;
    }

    @Bindable
    public String getDetail() {
        return detail;
    }

    public void setName(String name) {
        this.name = name;
//        notifyPropertyChanged(BR.name);
    }

    public void setAge(int age) {
        this.age = age;
//        notifyPropertyChanged(BR.age);
    }

    public void setAmount(float amount) {
        this.amount = amount;
//        notifyPropertyChanged(BR.amount);
    }

    public void setDetail(String detail) {
        this.detail = detail;
//        notifyPropertyChanged(BR.detail);
    }
}
