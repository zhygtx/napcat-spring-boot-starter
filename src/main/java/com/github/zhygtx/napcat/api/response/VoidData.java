package com.github.zhygtx.napcat.api.response;

/**
 * 无返回数据的 API 共用响应类型。
 * <p>
 * 对于 delete_msg、set_group_ban、set_group_kick 等操作类 API，
 * data 字段可能为 null 或空对象，使用此类型统一处理。
 */
@SuppressWarnings("unused")
public final class VoidData {

    /** 单例实例 */
    public static final VoidData INSTANCE = new VoidData();

    private VoidData() {
    }
}
