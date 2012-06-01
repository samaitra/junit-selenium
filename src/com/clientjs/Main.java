/**
 * 
 */
package com.clientjs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author samaitra
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestPostComment.class,
		TestPostBlankComment.class, TestPostReplyComment.class,
		TestRating.class, TestReporting.class, TestSortBy.class,
		TestHideComment.class,TestCommentCountIncrement.class,TestPagination.class
		})
public class Main {

}
