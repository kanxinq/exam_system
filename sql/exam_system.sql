
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '轮播备注',
                           `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '轮播图',
                           `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '跳转网址',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES (1, '第1个轮播图', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584927.jpg', 'http://baidu.com');
INSERT INTO `banner` VALUES (2, '第2个轮播图', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584926.jpg', 'http://baidu.com');
INSERT INTO `banner` VALUES (3, '第3个轮播图', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584927.jpg', 'http://baidu.com');
INSERT INTO `banner` VALUES (4, '第4个轮播图', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584926.jpg', 'http://baidu.com');
INSERT INTO `banner` VALUES (5, '跳转到百度', 'http://localhost:1000/file/exam/2024/06/09/20ccf13b-40d7-42c7-b747-ad211ab73f15.jpg', 'http://baidu.com');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '考试标题',
                         `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面图',
                         `start_time` datetime(0) NULL DEFAULT NULL COMMENT '考试开始时间',
                         `end_time` datetime(0) NULL DEFAULT NULL COMMENT '考试结束时间',
                         `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '试题列表',
                         `score` int(11) NULL DEFAULT NULL COMMENT '考试总分',
                         `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '考试表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES (1, '第一次考试', 'http://localhost:1000/file/exam/2024/06/09/4c95005f-87e0-4325-8a7d-6c1fd0f21724.jpg', '2024-03-17 00:00:00', '2024-06-13 00:00:00', '1,9,23', 30, 3);
INSERT INTO `exam` VALUES (2, '测试', 'http://localhost:1000/file/exam/2024/06/09/4c95005f-87e0-4325-8a7d-6c1fd0f21724.jpg', '2024-05-29 00:00:00', '2024-06-21 00:00:00', '1,2,4,5,6', 50, 3);
INSERT INTO `exam` VALUES (3, '123', 'http://localhost:1000/file/exam/2024/06/09/4c95005f-87e0-4325-8a7d-6c1fd0f21724.jpg', '2024-06-08 08:00:00', '2024-06-12 08:00:00', '1,2,4,5,25,24,22,21,23,20,19', 110, 3);

-- ----------------------------
-- Table structure for exam_question
-- ----------------------------
DROP TABLE IF EXISTS `exam_question`;
CREATE TABLE `exam_question`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '题目文本',
                                  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '题型',
                                  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '选择题选项',
                                  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '正确答案',
                                  `score` int(11) NULL DEFAULT NULL COMMENT '分值',
                                  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '试题表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam_question
-- ----------------------------
INSERT INTO `exam_question` VALUES (1, '什么是数据库?', '单选题', '[\"A. 数据存储\", \"B. 数据库\", \"C. 数据表\", \"D. 数据项\"]', 'A', 10, 3);
INSERT INTO `exam_question` VALUES (2, '以下哪种语言是面向对象的?', '单选题', '[\"A. Java\", \"B. C\", \"C. Assembly\", \"D. Python\"]', 'A', 10, 3);
INSERT INTO `exam_question` VALUES (4, 'HTML是什么的缩写?', '单选题', '[\"A. HyperText Markup Language\", \"B. HyperText Modeling Language\", \"C. HyperText Management Language\", \"D. HyperText Method Language\"]', 'A', 10, 3);
INSERT INTO `exam_question` VALUES (5, '以下哪些是Python的数据类型?', '多选题', '[\"A. Cascading Style Sheets\", \"B. Centralized Style Sheets\", \"C. Creative Style Sheets\", \"D. Computer Style Sheets\"]', 'ABCD', 10, 3);
INSERT INTO `exam_question` VALUES (6, '什么是CSS?', '单选题', '[\"A. Cascading Style Sheets\", \"B. Centralized Style Sheets\", \"C. Creative Style Sheets\", \"D. Computer Style Sheets\"]', 'A', 10, 3);
INSERT INTO `exam_question` VALUES (9, 'React是哪家公司开发的?', '多选题', '[\"A. Facebook\", \"B. Google\", \"C. Microsoft\", \"D. Apple\"]', 'ABCD', 10, 3);
INSERT INTO `exam_question` VALUES (10, '机器学习的主要分类有哪些?', '多选题', '[\"A. Cascading Style Sheets\", \"B. Centralized Style Sheets\", \"C. Creative Style Sheets\", \"D. Computer Style Sheets\"]', 'ABCD', 10, 3);
INSERT INTO `exam_question` VALUES (13, '1+2=？', '单选题', '[\"A. 1\", \"B. 2\", \"C. 3\", \"D. 4\"]', 'C', 10, 3);
INSERT INTO `exam_question` VALUES (14, '1+1+1+1=？', '单选题', '[\"A. 1\", \"B. 2\", \"C. 3\", \"D. 4\"]', 'D', 10, 3);
INSERT INTO `exam_question` VALUES (15, '1+0=？', '单选题', '[\"A. 1\", \"B. 22222\", \"C. 3\", \"D. 4\"]\n', '2', 10, 3);
INSERT INTO `exam_question` VALUES (16, '10+10=？', '单选题', '[\"A. 1\", \"B. 22222\", \"C. 3\", \"D. 20\"]\n', 'D', 10, 3);
INSERT INTO `exam_question` VALUES (17, '1+2+3=?', '单选题', '[\"A. 1\", \"B. 6\", \"C. 3\", \"D. 20\"]', 'D', 10, 3);
INSERT INTO `exam_question` VALUES (18, '1+10=?', '单选题', '[\"A：11\",\"B：22\",\"C：22\",\"D：113\"]', 'D', 10, 3);
INSERT INTO `exam_question` VALUES (19, '1', '单选题', '[\"A：1\",\"B：1\",\"C：1\",\"D：1\"]', 'D', 10, 3);
INSERT INTO `exam_question` VALUES (20, '1+1=2对吗', '判断题', '', '对', 10, 3);
INSERT INTO `exam_question` VALUES (21, '1+1=?', '填空题', '{\"0\":\"A：\",\"1\":\"B：\",\"2\":\"C：\",\"3\":\"D：\"}', '2', 10, 3);
INSERT INTO `exam_question` VALUES (22, '1+1=2?', '判断题', '', '对', 10, 3);
INSERT INTO `exam_question` VALUES (23, '1+1+1=3?', '判断题', '', '对', 10, 3);
INSERT INTO `exam_question` VALUES (24, '1+1=？', '单选题', '[\"A：1\",\"B：2\",\"C：3\",\"D：4\"]', 'B', 10, 3);
INSERT INTO `exam_question` VALUES (25, '简述一下计算机的组成原理？', '主观题', NULL, '计算机的组成原理', 10, 3);

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '网站名称',
                         `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '网站网址',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES (1, 'qq', 'https://im.qq.com');
INSERT INTO `link` VALUES (2, 'qq音乐', 'https://y.qq.com/');
INSERT INTO `link` VALUES (3, '百度', 'https://baidu.com');
INSERT INTO `link` VALUES (4, 'bilibili', 'https://bilibili.com');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
                         `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面图',
                         `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
                         `views` int(11) NULL DEFAULT NULL COMMENT '浏览量',
                         `create_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
                         `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布用户',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, 'XX考试备考攻略', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584927.jpg', '<p style=\"text-align: start;\">XX考试作为重要的选拔性考试，对于考生来说具有举足轻重的意义。然而，备考过程中如何高效利用时间提升成绩却是一个值得探讨的问题。以下是一些备考攻略供各位考生参考。</p><p style=\"text-align: start;\">首先，制定合理的复习计划是高效备考的关键。考生应根据自己的实际情况和考试要求制定详细的复习计划，并严格按照计划执行。在制定计划时，要充分考虑各科目的特点和难易程度合理分配时间。</p><p style=\"text-align: start;\">其次，注重基础知识的巩固和提高是提升成绩的有效途径。考生应重点复习考试大纲中的基础知识点和重点难点内容，并通过做题和模拟考试来检验自己的掌握程度。同时针对自己的薄弱环节进行有针对性的加强训练。</p><p style=\"text-align: start;\">此外，保持良好的作息习惯和心态也是高效备考的重要因素。考生应保证充足的睡眠和适当的运动以保持良好的身体状态；同时要学会调整心态保持积极乐观的态度面对备考过程中的挑战和困难。</p><p style=\"text-align: start;\">最后我们提醒考生们不要忽视考试技巧和答题方法的训练。掌握正确的答题技巧和方法能够在考试中更好地发挥自己的水平提高成绩。希望以上备考攻略能够帮助各位考生顺利备考取得优异成绩！</p>', 3, '2024-06-09 18:14:40', 'admin');
INSERT INTO `news` VALUES (2, 'XX考试改革方案发布', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584926.jpg', '<p style=\"text-align: start;\">XX考试作为重要的选拔性考试，对于考生来说具有举足轻重的意义。然而，备考过程中如何高效利用时间提升成绩却是一个值得探讨的问题。以下是一些备考攻略供各位考生参考。</p><p style=\"text-align: start;\">首先，制定合理的复习计划是高效备考的关键。考生应根据自己的实际情况和考试要求制定详细的复习计划，并严格按照计划执行。在制定计划时，要充分考虑各科目的特点和难易程度合理分配时间。</p><p style=\"text-align: start;\">其次，注重基础知识的巩固和提高是提升成绩的有效途径。考生应重点复习考试大纲中的基础知识点和重点难点内容，并通过做题和模拟考试来检验自己的掌握程度。同时针对自己的薄弱环节进行有针对性的加强训练。</p><p style=\"text-align: start;\">此外，保持良好的作息习惯和心态也是高效备考的重要因素。考生应保证充足的睡眠和适当的运动以保持良好的身体状态；同时要学会调整心态保持积极乐观的态度面对备考过程中的挑战和困难。</p><p style=\"text-align: start;\">最后我们提醒考生们不要忽视考试技巧和答题方法的训练。掌握正确的答题技巧和方法能够在考试中更好地发挥自己的水平提高成绩。希望以上备考攻略能够帮助各位考生顺利备考取得优异成绩！</p>', 4, '2024-06-09 18:14:40', 'admin');
INSERT INTO `news` VALUES (3, 'XX考试备考攻略', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584927.jpg', '<p style=\"text-align: start;\"><img src=\"http://localhost:1000/file/exam/2024/06/09/acbd227b-3cd0-44ec-be15-443a4aae34e8.jpg\" alt=\"购物商城系统-更新版(1) (1).jpg\" data-href=\"http://localhost:1000/file/exam/2024/06/09/acbd227b-3cd0-44ec-be15-443a4aae34e8.jpg\" style=\"\"></p><p style=\"text-align: start;\">XX考试作为重要的选拔性考试，对于考生来说具有举足轻重的意义。然而，备考过程中如何高效利用时间提升成绩却是一个值得探讨的问题。以下是一些备考攻略供各位考生参考。</p><p style=\"text-align: start;\">首先，制定合理的复习计划是高效备考的关键。考生应根据自己的实际情况和考试要求制定详细的复习计划，并严格按照计划执行。在制定计划时，要充分考虑各科目的特点和难易程度合理分配时间。</p><p style=\"text-align: start;\">其次，注重基础知识的巩固和提高是提升成绩的有效途径。考生应重点复习考试大纲中的基础知识点和重点难点内容，并通过做题和模拟考试来检验自己的掌握程度。同时针对自己的薄弱环节进行有针对性的加强训练。</p><p style=\"text-align: start;\">此外，保持良好的作息习惯和心态也是高效备考的重要因素。考生应保证充足的睡眠和适当的运动以保持良好的身体状态；同时要学会调整心态保持积极乐观的态度面对备考过程中的挑战和困难。</p><p style=\"text-align: start;\">最后我们提醒考生们不要忽视考试技巧和答题方法的训练。掌握正确的答题技巧和方法能够在考试中更好地发挥自己的水平提高成绩。希望以上备考攻略能够帮助各位考生顺利备考取得优异成绩！</p>', 8, '2024-06-09 18:14:40', 'admin');
INSERT INTO `news` VALUES (4, 'XX考试改革方案发布', 'http://localhost:1000/file/exam/2024/05/29/c0fcfda3-b57e-4b0d-b473-a3e795584926.jpg', '<p><br></p><p style=\"text-align: start;\">近日，教育部门发布了关于XX考试的最新改革方案，旨在进一步优化考试结构，提高考试的公平性和有效性。以下是本次改革方案的主要变化点，各位考生和家长需重点关注。</p><p style=\"text-align: start;\">首先，考试科目设置进行了调整，新增了XX科目，并取消了XX科目。这一变化旨在更好地反映现代社会对人才的需求，提高考生的综合素质。</p><p style=\"text-align: start;\">其次，考试形式也进行了创新。除了传统的笔试形式外，新增了XX考试环节，以全面考察考生的XX能力。这将有助于选拔出更具创新和实践能力的优秀人才。</p><p style=\"text-align: start;\">此外，本次改革还加强了对考试安全的监管力度，严厉打击作弊行为。考试机构将采取更加严格的监考措施和技术手段，确保考试的公平性和公正性。</p><p style=\"text-align: start;\">对于即将参加XX考试的考生来说，了解这些变化并针对性地进行备考至关重要。我们提醒考生们密切关注官方通知，及时了解考试动态，确保顺利备考。</p>', 204, '2024-06-09 18:14:40', 'admin');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
                           `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
                           `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                           `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布用户',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '栏目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '系统V1.0今天正式上线了', '系统V1.0今天正式上线了', '2024-09-11 00:00:00', 'admin');
INSERT INTO `notice` VALUES (2, '系统V1.0今天正式上线了', '系统V1.0今天正式上线了', '2024-09-11 00:00:00', 'admin');
INSERT INTO `notice` VALUES (3, '系统V1.0今天正式上线了', '系统V1.0今天正式上线了', '2024-09-11 00:00:00', 'admin');
INSERT INTO `notice` VALUES (4, '系统V1.0今天正式上线了', '系统V1.0今天正式上线了', '2024-09-11 00:00:00', 'admin');
INSERT INTO `notice` VALUES (5, '系统V1.0今天正式上线了', '系统V1.0今天正式上线了', '2024-09-11 00:00:00', 'admin');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `user_id` int(11) NULL DEFAULT NULL COMMENT '学生ID',
                          `exam_id` int(11) NULL DEFAULT NULL COMMENT '试题ID',
                          `teacher_id` int(11) NULL DEFAULT NULL COMMENT '教师ID',
                          `score` int(11) NULL DEFAULT NULL COMMENT '成绩',
                          `user_answers` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '学生提交的答案',
                          `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '考试名称',
                          `ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '题目ids',
                          `is_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否评分',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生成绩表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (11, 2, 3, 3, 48, '[{\"id\":1,\"name\":\"什么是数据库?\",\"type\":\"单选题\",\"options\":[\"A. 数据存储\",\"B. 数据库\",\"C. 数据表\",\"D. 数据项\"],\"answer\":\"A\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":0,\"userAnswers\":\"C\"},{\"id\":2,\"name\":\"以下哪种语言是面向对象的?\",\"type\":\"单选题\",\"options\":[\"A. Java\",\"B. C\",\"C. Assembly\",\"D. Python\"],\"answer\":\"A\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":0,\"userAnswers\":\"C\"},{\"id\":4,\"name\":\"HTML是什么的缩写?\",\"type\":\"单选题\",\"options\":[\"A. HyperText Markup Language\",\"B. HyperText Modeling Language\",\"C. HyperText Management Language\",\"D. HyperText Method Language\"],\"answer\":\"A\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":0,\"userAnswers\":\"C\"},{\"id\":24,\"name\":\"1+1=？\",\"type\":\"单选题\",\"options\":[\"A：1\",\"B：2\",\"C：3\",\"D：4\"],\"answer\":\"B\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":10,\"userAnswers\":\"B\"},{\"id\":19,\"name\":\"1\",\"type\":\"单选题\",\"options\":[\"A：1\",\"B：1\",\"C：1\",\"D：1\"],\"answer\":\"D\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":0,\"userAnswers\":\"B\"},{\"id\":5,\"name\":\"以下哪些是Python的数据类型?\",\"type\":\"多选题\",\"options\":[\"A. Cascading Style Sheets\",\"B. Centralized Style Sheets\",\"C. Creative Style Sheets\",\"D. Computer Style Sheets\"],\"answer\":\"ABCD\",\"score\":10,\"userId\":null,\"user\":null,\"userAnswers\":[\"C\"],\"userScore\":0},{\"id\":22,\"name\":\"1+1=2?\",\"type\":\"判断题\",\"options\":\"\",\"answer\":\"对\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":10,\"userAnswers\":\"对\"},{\"id\":23,\"name\":\"1+1+1=3?\",\"type\":\"判断题\",\"options\":\"\",\"answer\":\"对\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":10,\"userAnswers\":\"对\"},{\"id\":20,\"name\":\"1+1=2对吗\",\"type\":\"判断题\",\"options\":\"\",\"answer\":\"对\",\"score\":10,\"userId\":null,\"user\":null,\"userScore\":10,\"userAnswers\":\"对\"},{\"id\":21,\"name\":\"1+1=?\",\"type\":\"填空题\",\"options\":\"{\\\"0\\\":\\\"A：\\\",\\\"1\\\":\\\"B：\\\",\\\"2\\\":\\\"C：\\\",\\\"3\\\":\\\"D：\\\"}\",\"answer\":\"2\",\"score\":10,\"userId\":null,\"user\":null,\"userAnswers\":\"123\",\"userScore\":0},{\"id\":25,\"name\":\"简述一下计算机的组成原理？\",\"type\":\"主观题\",\"options\":null,\"answer\":\"计算机的组成原理\",\"score\":10,\"userId\":null,\"user\":null,\"userAnswers\":\"123\",\"userScore\":\"8\"}]', '123', '1,2,4,5,25,24,22,21,23,20,19', '已评分');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色名称',
                             `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色描述',
                             `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色字段',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '超级管理员', 'ADMIN');
INSERT INTO `sys_role` VALUES (2, '普通用户', '普通用户', 'USER');
INSERT INTO `sys_role` VALUES (3, '老师', '老师', 'TEACHER');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名称',
                             `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户密码',
                             `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '个人简介',
                             `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
                             `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
                             `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
                             `role_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
                             `salt` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '加盐',
                             `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '开始时间',
                             `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
                             `state` int(1) NULL DEFAULT 1 COMMENT '状态（正常/禁用）',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '141eaa05f027045768eedb4455157064', '', '12345@gmail.com', '1326548965', '', 'http://localhost:1000/file/exam/user.png', 'ADMIN', '0B7133911E736A47', '2023-06-19 11:24:56', '女', 1);
INSERT INTO `sys_user` VALUES (2, 'user', '6c9662bf14419e9658bd5d32acd08784', '', '231@qq.com', '1325647895', '', 'http://localhost:1000/file/exam/user.png', 'USER', 'B5CBDA399B5128A9', '2023-06-19 19:24:56', '男', 1);
INSERT INTO `sys_user` VALUES (3, 'teacher', '25d47d6ce5ad406b1f81f4898f5f6ff5', NULL, NULL, NULL, NULL, NULL, 'TEACHER', '95DFCAABA55227B3', '2024-06-04 20:41:56', '男', 1);

SET FOREIGN_KEY_CHECKS = 1;
