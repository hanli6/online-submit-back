package cloud.icode.onlinesubmit.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/24 下午9:00
 * 文件描述: ManuscriptVo
 */
@Data
public class ManuscriptVo {
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manuscript.title
     *
     * @mbg.generated Tue Jan 21 19:55:46 CST 2025
     */
    private String title;

    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manuscript.description
     *
     * @mbg.generated Tue Jan 21 19:55:46 CST 2025
     */
    private String nickName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manuscript.status
     *
     * @mbg.generated Tue Jan 21 19:55:46 CST 2025
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column manuscript.submit_time
     *
     * @mbg.generated Tue Jan 21 19:55:46 CST 2025
     */
    private Date submitTime;

    private String coverImg;
    private String fileName;

}
