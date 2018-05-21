package top.tywang.seckill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.seckill.dao.GoodsDao;
import top.tywang.seckill.domain.SecKillGoods;
import top.tywang.seckill.vo.GoodsVo;


@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goods) {
        SecKillGoods g = new SecKillGoods();
        g.setGoodsId(goods.getId());
        goodsDao.reduceStock(g);
    }


}
