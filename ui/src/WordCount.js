import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import axios from 'axios'

import WordCountResults from './WordCountResults';

class WordCount extends Component {
  constructor(props) {
    super(props);

    this.state = {
      totalWordCount: 0,
      counts: [],
    };
  }

  handleChange(selectorFiles) {
    const formData = new FormData();
    Array.from(selectorFiles).forEach(file => {
      formData.append(file.name, file);
    });

    return axios.post("/wordcount", formData, {
    }).then(response => {
      this.setState({totalWordCount : response.data.totalWordCount, counts : response.data.counts})
    })
  }

  render() {
    var inputStyle = {
      position: "absolute",
      display: "none"
    }

    return (
      <div>
        <input
          accept="text/plain"
          style={inputStyle}
          id="file-upload"
          type="file"
          onChange={ (e) => this.handleChange(e.target.files) }
        />
        <label htmlFor="file-upload">
          <Button variant="raised" component="span">
            Upload
          </Button>
        </label>

        <WordCountResults totalWordCount={this.state.totalWordCount} counts={this.state.counts}/>
      </div>
    );
  }
}
export default WordCount;
