package reader;

//import java.sql.Timestamp;

//import java.sql.Date;

public class RFIDdata {
	
	String tagid;
	String readerid;
	String doorid;
	Boolean valid;
//	String timeattempted;
//	String roomid;
	
	
	public RFIDdata(String tagid, String readerid, String doorid, Boolean valid) /*String timeattempted, String roomid)*/ {
		super();
		this.tagid = tagid;
		this.readerid = readerid;
		this.doorid = doorid;
		this.valid = valid;
		//this.timeattempted = timeattempted;
		//this.roomid = roomid;
		}

	
	public RFIDdata(String tagid, String readerid) {
		super();
		this.tagid = tagid;
		this.readerid = readerid;
		//defaults for when not known:
		this.doorid = null;
		this.valid = false;
		//this.timeattempted = "unknowndate";
	//	this.roomid = "Room205";
	}
	
	
/**
 * 
 * @return tagid
 */
	public String getTagid() {
		return tagid;
	}
/**
* 
* 
* @param set the tagid 
*/
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	
/**
 * 
 * @return get readerid
 */
	public String getReaderid() {
		return readerid;
	}
/**
	* 
	* 
	* @param set the readerid 
*/
	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}
		
		/**
		 * 
		 * @return doorid
		 */
	public String getDoorid() {
		return doorid;
	}
		/**
		* 
		* 
		* @param set the doorid 
		*/
	public void setDoorid(String doorid) {
		this.doorid = doorid;
	}
			
/**
 * 
* @return valid boolean
*/
	public Boolean getValid() {
		return valid;
	}				

   public void setValid(Boolean valid) {
		this.valid = valid;
	}

			

	@Override
	public String toString() {
		return "RFIDdata [tagid=" + tagid + ", readerid=" + readerid + ", doorid=" + doorid
				+ ", validation=" + valid + "]";
	}
	
	
	
}
