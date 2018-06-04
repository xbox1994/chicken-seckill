package top.tywang.secKill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.secKill.dao.SecKillGoodsDao;
import top.tywang.secKill.domain.SecKillGoods;
import top.tywang.secKill.vo.SecKillGoodsVo;


@Service
public class GoodsService {

    @Autowired
    SecKillGoodsDao secKillGoodsDao;

    public List<SecKillGoodsVo> listGoodsVo() {
        return secKillGoodsDao.listSecKillGoodsVo();
    }

    public SecKillGoodsVo getGoodsVoByGoodsId(long goodsId) {
        return secKillGoodsDao.getSecKillGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(SecKillGoodsVo goods) {
        SecKillGoods g = new SecKillGoods();
        g.setGoodsId(goods.getId());
        secKillGoodsDao.reduceStock(g);
    }


}
