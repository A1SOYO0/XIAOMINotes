/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.micode.notes.gtask.data;

import android.database.Cursor;

import org.json.JSONObject;

/**
 * Node类是数据同步架构的核心抽象类
 * 
 * 该类定义了笔记和任务数据在本地与远程服务器间同步的基本结构和行为。
 * 子类需要实现特定的同步逻辑，而Node类提供了通用的属性和方法框架。
 * 每个Node对象代表一个可同步的实体（如笔记或文件夹），包含其标识符、
 * 名称、修改时间和删除状态等基本信息。
 */
public abstract class Node {
    /** 无需同步动作 */
    public static final int SYNC_ACTION_NONE = 0;

    /** 需要添加到远程服务器 */
    public static final int SYNC_ACTION_ADD_REMOTE = 1;

    /** 需要添加到本地数据库 */
    public static final int SYNC_ACTION_ADD_LOCAL = 2;

    /** 需要从远程服务器删除 */
    public static final int SYNC_ACTION_DEL_REMOTE = 3;

    /** 需要从本地数据库删除 */
    public static final int SYNC_ACTION_DEL_LOCAL = 4;

    /** 需要更新到远程服务器 */
    public static final int SYNC_ACTION_UPDATE_REMOTE = 5;

    /** 需要更新到本地数据库 */
    public static final int SYNC_ACTION_UPDATE_LOCAL = 6;

    /** 本地和远程数据有冲突 */
    public static final int SYNC_ACTION_UPDATE_CONFLICT = 7;

    /** 同步过程中发生错误 */
    public static final int SYNC_ACTION_ERROR = 8;

    /** 谷歌任务ID */
    private String mGid;

    /** 节点名称 */
    private String mName;

    /** 最后修改时间戳 */
    private long mLastModified;

    /** 删除状态标记 */
    private boolean mDeleted;

    /**
     * 构造函数，初始化基本属性
     */
    public Node() {
        mGid = null;
        mName = "";
        mLastModified = 0;
        mDeleted = false;
    }

    /**
     * 获取创建操作的JSON对象
     * @param actionId 同步操作的动作ID
     * @return 用于创建节点的JSON对象
     */
    public abstract JSONObject getCreateAction(int actionId);

    /**
     * 获取更新操作的JSON对象
     * @param actionId 同步操作的动作ID
     * @return 用于更新节点的JSON对象
     */
    public abstract JSONObject getUpdateAction(int actionId);

    /**
     * 根据远程JSON数据设置内容
     * @param js 从远程服务器获取的JSON对象
     */
    public abstract void setContentByRemoteJSON(JSONObject js);

    /**
     * 根据本地JSON数据设置内容
     * @param js 从本地数据库获取的JSON对象
     */
    public abstract void setContentByLocalJSON(JSONObject js);

    /**
     * 将当前内容转换为本地JSON对象
     * @return 表示当前内容的JSON对象
     */
    public abstract JSONObject getLocalJSONFromContent();

    /**
     * 根据数据库游标确定同步操作类型
     * @param c 数据库查询结果游标
     * @return 同步操作的类型常量
     */
    public abstract int getSyncAction(Cursor c);

    /**
     * 设置谷歌任务ID
     * @param gid 谷歌任务的唯一标识符
     */
    public void setGid(String gid) {
        this.mGid = gid;
    }

    /**
     * 设置节点名称
     * @param name 节点的名称
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * 设置最后修改时间
     * @param lastModified 最后修改的时间戳
     */
    public void setLastModified(long lastModified) {
        this.mLastModified = lastModified;
    }

    /**
     * 设置删除状态
     * @param deleted 是否标记为已删除
     */
    public void setDeleted(boolean deleted) {
        this.mDeleted = deleted;
    }

    /**
     * 获取谷歌任务ID
     * @return 谷歌任务的唯一标识符
     */
    public String getGid() {
        return this.mGid;
    }

    /**
     * 获取节点名称
     * @return 节点的名称
     */
    public String getName() {
        return this.mName;
    }

    /**
     * 获取最后修改时间
     * @return 最后修改的时间戳
     */
    public long getLastModified() {
        return this.mLastModified;
    }

    /**
     * 获取删除状态
     * @return 是否标记为已删除
     */
    public boolean getDeleted() {
        return this.mDeleted;
    }

}
