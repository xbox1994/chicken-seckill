package top.tywang.secKill.rabbitmq;

import lombok.*;
import top.tywang.secKill.domain.SecKillUser;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SecKillMessage {
    private SecKillUser secKillUser;
    private long goodsId;
}
