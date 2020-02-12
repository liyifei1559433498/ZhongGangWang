package com.zgw.home.model;


import android.os.Parcel;

import com.zgw.base.model.BaseBean;

public class ReturnBean extends BaseBean {

	private String result = "";
	private String msg_code = "";
	private String message = "";

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg_code() {
		return msg_code;
	}

	public void setMsg_code(String msg_code) {
		this.msg_code = msg_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(result);
		dest.writeString(msg_code);
		dest.writeString(message);
	}

	public static final Creator<ReturnBean> CREATOR = new Creator<ReturnBean>() {

		@Override
		public ReturnBean createFromParcel(Parcel source) {
			ReturnBean bean = new ReturnBean();
			bean.result = source.readString();
			bean.msg_code = source.readString();
			bean.message = source.readString();
			return bean;
		}

		@Override
		public ReturnBean[] newArray(int size) {
			return new ReturnBean[size];
		}
	};
	
}
