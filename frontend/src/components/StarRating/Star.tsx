import React from 'react';

const Star = ({ selected = false, onClick }) => (
  <span className={selected ? "star selected" : "star"} onClick={onClick}>
    &#9733;
  </span>
);

export default Star;
