package com.zgw.base.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseBean implements Parcelable {
	/**请求码*/
    protected String requestCode;
    
	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(requestCode);
	}
	public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {

		@Override
		public BaseBean createFromParcel(Parcel source) {
			
			BaseBean bean = new BaseBean();
			bean.requestCode =  source.readString();

 			return bean;
		}

		@Override
		public BaseBean[] newArray(int size) {
			return new BaseBean[size];
		}
	};

}
