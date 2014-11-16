package br.unb.mobileMedia.core.domain;

import br.unb.mobileMedia.core.db.AudioDao;
import br.unb.mobileMedia.core.db.DaoSession;
import br.unb.mobileMedia.core.db.HistoryDao;
import br.unb.mobileMedia.core.db.VideoDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table HISTORY.
 */
public class History {

    private Long id;
    private long audioHistoryId;
    private long videoHistoryId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient HistoryDao myDao;

    private Audio audio;
    private Long audio__resolvedKey;

    private Video video;
    private Long video__resolvedKey;


    public History() {
    }

    public History(Long id) {
        this.id = id;
    }

    public History(Long id, long audioHistoryId, long videoHistoryId) {
        this.id = id;
        this.audioHistoryId = audioHistoryId;
        this.videoHistoryId = videoHistoryId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHistoryDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAudioHistoryId() {
        return audioHistoryId;
    }

    public void setAudioHistoryId(long audioHistoryId) {
        this.audioHistoryId = audioHistoryId;
    }

    public long getVideoHistoryId() {
        return videoHistoryId;
    }

    public void setVideoHistoryId(long videoHistoryId) {
        this.videoHistoryId = videoHistoryId;
    }

    /** To-one relationship, resolved on first access. */
    public Audio getAudio() {
        long __key = this.audioHistoryId;
        if (audio__resolvedKey == null || !audio__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AudioDao targetDao = daoSession.getAudioDao();
            Audio audioNew = targetDao.load(__key);
            synchronized (this) {
                audio = audioNew;
            	audio__resolvedKey = __key;
            }
        }
        return audio;
    }

    public void setAudio(Audio audio) {
        if (audio == null) {
            throw new DaoException("To-one property 'audioHistoryId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.audio = audio;
            audioHistoryId = audio.getId();
            audio__resolvedKey = audioHistoryId;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Video getVideo() {
        long __key = this.videoHistoryId;
        if (video__resolvedKey == null || !video__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VideoDao targetDao = daoSession.getVideoDao();
            Video videoNew = targetDao.load(__key);
            synchronized (this) {
                video = videoNew;
            	video__resolvedKey = __key;
            }
        }
        return video;
    }

    public void setVideo(Video video) {
        if (video == null) {
            throw new DaoException("To-one property 'videoHistoryId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.video = video;
            videoHistoryId = video.getId();
            video__resolvedKey = videoHistoryId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
