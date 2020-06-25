package data_p.product_p.room_p;

import java.util.ArrayList;
import java.util.Calendar;

import data_p.product_p.ProductData;
import data_p.product_p.TimeData;

//룸 하나의 여러개의 시간 상품 
public class RoomProduct extends ProductData {

	public boolean isExit;
	public Integer personNum;
	public String userUUID;
	public ArrayList<Calendar> calendarList = new ArrayList<Calendar>();;

	public RoomProduct(int id, String name, long price, int personNum) {
		super(id, name, price);
		this.personNum = personNum;
		isExit = false;
	}

	// 날짜 입력
	public void setDate(String uuid, ArrayList<Calendar> calendarList) {

		userUUID = uuid;
		for (Calendar cal : calendarList) {
			System.out.println(cal.getTime());
		}
		this.calendarList = calendarList;
	}

	public void setExit() {
		isExit = true;
	}

	public RoomProduct getClone() {     
		RoomProduct clone = new RoomProduct(this.id, this.name, this.price, this.personNum);
		clone.userUUID = userUUID;
		clone.isExit = isExit;
		return clone;
	}
}
