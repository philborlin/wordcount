import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import axios from 'axios'

class WordCount extends Component {
  constructor(props) {
    super(props);

    this.state = {
      openUploadModal: false,
      files: [],
    };
  }

  handleChange(selectorFiles) {
    const formData = new FormData();
    Array.from(selectorFiles).forEach(file => {
      formData.append(file.name, file);
    });

    return axios.post("/wordcount", formData, {
    }).then(response => {
      const data = response.data;
      console.log(data);
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
          className={this.props.input}
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
      </div>
    );
  }
}
export default WordCount;
