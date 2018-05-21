package top.tywang.seckill.domain;

import lombok.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author dongwei
 * @date 2018/05/21
 * Time: 18:34
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecKillGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
}
