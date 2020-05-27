package com.tatvasoft.nativepost.ui.post.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostResponseModel{

	@SerializedName("hits")
	private List<HitsItem> hits;

	@SerializedName("hitsPerPage")
	private int hitsPerPage;

	@SerializedName("processingTimeMS")
	private int processingTimeMS;

	@SerializedName("query")
	private String query;

	@SerializedName("nbHits")
	private int nbHits;

	@SerializedName("page")
	private int page;

	@SerializedName("params")
	private String params;

	@SerializedName("nbPages")
	private int nbPages;

	@SerializedName("exhaustiveNbHits")
	private boolean exhaustiveNbHits;

	public List<HitsItem> getHits(){
		return hits;
	}

	public int getHitsPerPage(){
		return hitsPerPage;
	}

	public int getProcessingTimeMS(){
		return processingTimeMS;
	}

	public String getQuery(){
		return query;
	}

	public int getNbHits(){
		return nbHits;
	}

	public int getPage(){
		return page;
	}

	public String getParams(){
		return params;
	}

	public int getNbPages(){
		return nbPages;
	}

	public boolean isExhaustiveNbHits(){
		return exhaustiveNbHits;
	}

	public static class Url{

		@SerializedName("matchLevel")
		private String matchLevel;

		@SerializedName("value")
		private String value;

		@SerializedName("matchedWords")
		private List<Object> matchedWords;

		public String getMatchLevel(){
			return matchLevel;
		}

		public String getValue(){
			return value;
		}

		public List<Object> getMatchedWords(){
			return matchedWords;
		}
	}

	public static class Title{

		@SerializedName("matchLevel")
		private String matchLevel;

		@SerializedName("value")
		private String value;

		@SerializedName("matchedWords")
		private List<Object> matchedWords;

		public String getMatchLevel(){
			return matchLevel;
		}

		public String getValue(){
			return value;
		}

		public List<Object> getMatchedWords(){
			return matchedWords;
		}
	}

	public static class StoryText{

		@SerializedName("matchLevel")
		private String matchLevel;

		@SerializedName("value")
		private String value;

		@SerializedName("matchedWords")
		private List<Object> matchedWords;

		public String getMatchLevel(){
			return matchLevel;
		}

		public String getValue(){
			return value;
		}

		public List<Object> getMatchedWords(){
			return matchedWords;
		}
	}

	public static class HitsItem{

		boolean isCheck;

		public HitsItem(boolean isCheck) {
			this.isCheck = isCheck;
		}

		public boolean isCheck() {
			return isCheck;
		}

		public void setCheck(boolean check) {
			isCheck = check;
		}

		@SerializedName("comment_text")
		private Object commentText;

		@SerializedName("story_text")
		private Object storyText;

		@SerializedName("author")
		private String author;

		@SerializedName("story_id")
		private Object storyId;

		@SerializedName("_tags")
		private List<String> tags;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("created_at_i")
		private int createdAtI;

		@SerializedName("title")
		private String title;

		@SerializedName("url")
		private String url;

		@SerializedName("points")
		private int points;

		@SerializedName("_highlightResult")
		private HighlightResult highlightResult;

		@SerializedName("num_comments")
		private int numComments;

		@SerializedName("story_title")
		private Object storyTitle;

		@SerializedName("parent_id")
		private Object parentId;

		@SerializedName("story_url")
		private Object storyUrl;

		@SerializedName("objectID")
		private String objectID;


		public Object getCommentText(){
			return commentText;
		}

		public Object getStoryText(){
			return storyText;
		}

		public String getAuthor(){
			return author;
		}

		public Object getStoryId(){
			return storyId;
		}

		public List<String> getTags(){
			return tags;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getCreatedAtI(){
			return createdAtI;
		}

		public String getTitle(){
			return title;
		}

		public String getUrl(){
			return url;
		}

		public int getPoints(){
			return points;
		}

		public HighlightResult getHighlightResult(){
			return highlightResult;
		}

		public int getNumComments(){
			return numComments;
		}

		public Object getStoryTitle(){
			return storyTitle;
		}

		public Object getParentId(){
			return parentId;
		}

		public Object getStoryUrl(){
			return storyUrl;
		}

		public String getObjectID(){
			return objectID;
		}
	}

	public static class HighlightResult{

		@SerializedName("author")
		private Author author;

		@SerializedName("title")
		private Title title;

		@SerializedName("url")
		private Url url;

		@SerializedName("story_text")
		private StoryText storyText;

		public Author getAuthor(){
			return author;
		}

		public Title getTitle(){
			return title;
		}

		public Url getUrl(){
			return url;
		}

		public StoryText getStoryText(){
			return storyText;
		}
	}

	public static class Author{

		@SerializedName("matchLevel")
		private String matchLevel;

		@SerializedName("value")
		private String value;

		@SerializedName("matchedWords")
		private List<Object> matchedWords;

		public String getMatchLevel(){
			return matchLevel;
		}

		public String getValue(){
			return value;
		}

		public List<Object> getMatchedWords(){
			return matchedWords;
		}
	}
}