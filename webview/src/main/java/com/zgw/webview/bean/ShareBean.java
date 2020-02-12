package com.zgw.webview.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareBean implements Parcelable {
	private String shareDesc;//分享内容
	private String shareLinkUrl;//分享url
	private String shareTitle;//分享标题
	private String shareImgUrl;//分享图片url

	public String getShareDesc() {
		return shareDesc;
	}

	public String getShareLinkUrl() {
		return shareLinkUrl;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public String getShareImgUrl() {
		return shareImgUrl;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	public void setShareLinkUrl(String shareLinkUrl) {
		this.shareLinkUrl = shareLinkUrl;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public void setShareImgUrl(String shareImgUrl) {
		this.shareImgUrl = shareImgUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(shareDesc);
		dest.writeString(shareLinkUrl);
		dest.writeString(shareTitle);
		dest.writeString(shareImgUrl);
	}
	public static final Creator<ShareBean> CREATOR = new Creator<ShareBean>() {

		@Override
		public ShareBean createFromParcel(Parcel source) {
			ShareBean bean = new ShareBean();
			bean.shareDesc = source.readString();
			bean.shareLinkUrl = source.readString();
			bean.shareTitle = source.readString();
			bean.shareImgUrl = source.readString();
 			return bean;
		}

		@Override
		public ShareBean[] newArray(int size) {
			return new ShareBean[size];
		}
	};
	
}
