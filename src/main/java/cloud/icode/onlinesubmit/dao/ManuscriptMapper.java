package cloud.icode.onlinesubmit.dao;

import cloud.icode.onlinesubmit.model.Manuscript;
import cloud.icode.onlinesubmit.model.ManuscriptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ManuscriptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    long countByExample(ManuscriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int deleteByExample(ManuscriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int insert(Manuscript row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int insertSelective(Manuscript row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    List<Manuscript> selectByExampleWithBLOBs(ManuscriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    List<Manuscript> selectByExample(ManuscriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    Manuscript selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int updateByExampleSelective(@Param("row") Manuscript row, @Param("example") ManuscriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int updateByExampleWithBLOBs(@Param("row") Manuscript row, @Param("example") ManuscriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int updateByExample(@Param("row") Manuscript row, @Param("example") ManuscriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int updateByPrimaryKeySelective(Manuscript row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int updateByPrimaryKeyWithBLOBs(Manuscript row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manuscript
     *
     * @mbg.generated Mon Jan 27 19:07:46 CST 2025
     */
    int updateByPrimaryKey(Manuscript row);
}