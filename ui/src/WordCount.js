import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import axios from 'axios'

import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import CircularProgress from '@material-ui/core/CircularProgress';

import WordCountResults from './WordCountResults';

class WordCount extends Component {
  constructor(props) {
    super(props);

    this.state = {
      totalWordCount: 0,
      counts: [],
      shouldHide: true,
      showProgress: false,
    };
  }

  clear() {
    this.setState({totalWordCount : 0, counts : [], shouldHide : true})
  }

  handleChange(e) {
    // Make the event available asynchronously
    e.persist()

    const formData = new FormData();
    Array.from(e.target.files).forEach(file => {
      formData.append(file.name, file);
    });

    this.setState({
      showProgress: true,
      shouldHide: true,
    })

    axios.post("/wordcount", formData, {
    }).then(response => {
      // Clear the FileUpload state so it can be reused
      e.target.value = null;
      this.setState({
        totalWordCount: response.data.totalWordCount,
        counts: response.data.counts,
        shouldHide: false,
        showProgress: false,
      })
    }).catch(response => {
      e.target.value = null;
      this.setState({
        showProgress: false,
      })
    })
  }

  render() {
    var inputStyle = {
      position: "absolute",
      display: "none"
    }

    return (
      <div>
        <Grid container justify="space-around" spacing={24}>
          <Grid item xs={10}>
            <Card>
              <CardContent>
                <Typography variant="headline" component="h2">
                  Word Count
                </Typography>
                <Typography component="h2">
                  Click the Upload button to upload a file of type text/plain
                  and we'll analyze the file and give you a report of all the
                  words in your file.
                </Typography>
              </CardContent>
              <CardActions>
                <input
                  accept="text/plain"
                  style={inputStyle}
                  id="file-upload"
                  type="file"
                  onChange={ (e) => this.handleChange(e) }
                />
                <label htmlFor="file-upload">
                  <Button component="span">
                    Upload
                  </Button>
                </label>

                <Button component="span" onClick={ (e) => this.clear() }>
                  Clear
                </Button>
              </CardActions>
            </Card>
          </Grid>

          <Grid container item xs={6} justify="center" style={this.state.showProgress ? {} : { display: 'none' } }>
            <CircularProgress/>
          </Grid>
          <Grid item xs={6} style={this.state.shouldHide ? { display: 'none' } : {} }>
            <WordCountResults totalWordCount={this.state.totalWordCount} counts={this.state.counts}/>
          </Grid>
        </Grid>
      </div>
    );
  }
}
export default WordCount;
