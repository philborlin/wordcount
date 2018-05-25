import React, {Component} from 'react';

import './App.css';
import WordCount from './WordCount.js';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {title: ''};
  }

  render() {
    return (
      <div>
        <WordCount/>
      </div>
    );
  }
}
export default App;
