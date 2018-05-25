package com.e3mall.front.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "tb_item")
public class TbItem implements Serializable{
	@Id
//	@GeneratedValue(generator = "paymentableGenerator")     
//	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
//	@Column(name = "id")
    private Long id;
	
	// @ManyToOne
//	 @JoinColumn(name="cid")	
//    private TbItemCat category;
	private long cid; 
	 
    private String title;

    private String sellPoint;

    private Long price;

    private Integer num;

    private String barcode;

    private String image;

    private Byte status;

    private Date created;

    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

 

//    public TbItemCat getCategory() {
//		return category;
//	}
//
//	public void setCategory(TbItemCat category) {
//		this.category = category;
//	}

	public Byte getStatus() {
        return status;
    }

    public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

	@Override
	public String toString() {
		return "TbItem [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", num="
				+ num + ", barcode=" + barcode + ", image=" + image + ", status=" + status
				+ ", created=" + created + ", updated=" + updated + "]";
	}
    
}