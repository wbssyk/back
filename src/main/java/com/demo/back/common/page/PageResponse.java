package com.demo.back.common.page;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.Map;

public class PageResponse extends ServiceResponse {
    private static final long serialVersionUID = 1L;

    //每页个数
    private Integer offset;
    //总页数
    private Integer pages;
    //当前页
    private Integer current;
    //总条数
    private Integer total;

    public PageResponse() {
        super();
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static PageResponse createResponse(Integer total, Object data, PageRequest request) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setTotal(total);
        pageResponse.setCurrent(request.getCurrent());
        pageResponse.setData(data);
        if (request.getSize() != null) {
            pageResponse.setOffset(request.getOffset());
            pageResponse.setPages(total % request.getOffset() == 0 ? total / request.getOffset() : total / request.getOffset() + 1);
            if (pageResponse.getPages() == 0) {
                pageResponse.setPages(1);
            }
        }
        return pageResponse;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    /**
     * 页码计算
     * @param request
     * @param count
     */
    public void setResponsePages(Map<String, Object> request, Integer count) {
        if (request.get("offset") != null) {
            this.offset = ((Integer) request.get("offset"));
            this.pages = (count % (Integer) request.get("offset") == 0 ? count / (Integer) request.get("offset") : count / (Integer) request.get("offset") + 1);
            if (this.pages == 0) {
                this.pages = 1;
            }
        }
    }

}
