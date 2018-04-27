package com.vrv.monitor.datapicker.dao.assetConfig;

import com.vrv.monitor.datapicker.model.assetConfig.Asset;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dendi on 2017/10/18.
 */
@Repository
public interface AssetDao {
    List<Asset> queryByProtocol(String protocol,String networkClass,String areaCode);
}
