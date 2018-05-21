package top.tywang.seckill.domain;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author dongwei
 * @date 2018/05/21
 * Time: 18:35
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecKillOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
