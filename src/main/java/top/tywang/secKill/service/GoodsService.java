package top.tywang.secKill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.secKill.dao.SecKillGoodsDao;
import top.tywang.secKill.domain.SecKillGoods;
import top.tywang.secKill.vo.SecKillGoodsVo;

import java.util.List;


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

    public boolean reduceStock(SecKillGoodsVo goods) {
        SecKillGoods g = new SecKillGoods();
        g.setGoodsId(goods.getId());
        return secKillGoodsDao.reduceStock(g) > 0;
    }


    public void resetStock(List<SecKillGoodsVo> goodsList) {
        for(SecKillGoodsVo goods : goodsList ) {
            SecKillGoods g = new SecKillGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            secKillGoodsDao.resetStock(g);
        }
    }


}
