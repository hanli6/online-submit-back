package cloud.icode.onlinesubmit.dao;

import cloud.icode.onlinesubmit.model.Menu;
import cloud.icode.onlinesubmit.model.MenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    long countByExample(MenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int deleteByExample(MenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int insert(Menu row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int insertSelective(Menu row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    List<Menu> selectByExample(MenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    Menu selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int updateByExampleSelective(@Param("row") Menu row, @Param("example") MenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int updateByExample(@Param("row") Menu row, @Param("example") MenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int updateByPrimaryKeySelective(Menu row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Jan 12 10:53:56 CST 2025
     */
    int updateByPrimaryKey(Menu row);
}