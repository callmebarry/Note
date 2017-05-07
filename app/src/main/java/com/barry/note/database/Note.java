package com.barry.note.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/7.
 */
@Entity
public class Note implements Serializable {

    @Id
    public Long id;

    String title;

    public String content;

    private Date createTime;

    private boolean Clock;

    public boolean getClock() {
        return this.Clock;
    }

    public void setClock(boolean Clock) {
        this.Clock = Clock;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1902987711)
    public Note(Long id, String title, String content, Date createTime,
            boolean Clock) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
            this.Clock = Clock;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

}
