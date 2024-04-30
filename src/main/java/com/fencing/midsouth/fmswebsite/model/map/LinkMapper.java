package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.LinkForm;
import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Link;

public class LinkMapper {
    public static Link map(LinkForm linkForm, Club club) {
        return new Link(linkForm.getName(), linkForm.getAddress(), club);
    }
}
