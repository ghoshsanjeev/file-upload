import React from 'react';
import { render } from 'react-dom';

import styled from 'styled-components';
import { Tabs, TabList, Tab, TabPanel } from 'react-tabs';

export const STabs = styled(Tabs)`
  font-family: BlinkMacSystemFont, "Segoe UI", sans-serif;
  font-size: 12px;
  width: 100%;
`;

export const STabList = styled(TabList)`
  list-style-type: none;
  padding: 4px;
  display: flex;
  margin: 0;
`;
STabList.tabsRole = 'TabList';

export const STab = styled(Tab)`
  margin-right: 4px;
  border: 1px solid black;
  padding: 4px;
  user-select: none;
  cursor: arrow;

  &.is-selected {
    // color: white;
    // background: black;
    border-bottom: 1px solid white;
  }

  &:focus {
    outline: none;
    box-shadow: 0 0 0 2px rgba(0, 0, 255, .5)
  }
`;
STab.tabsRole = 'Tab';

export const STabPanel = styled(TabPanel)`
  display: none;
  min-height: 40vh;
  border: 1px solid black;
  padding: 4px;
  margin-top: -5px;

  &.is-selected {
    display: block;
  }
`;
STabPanel.tabsRole = 'TabPanel';


