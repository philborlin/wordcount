import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import axios from 'axios'

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';

class WordCount extends Component {
  constructor(props) {
    super(props);

    this.state = {
      openUploadModal: false,
      files: [],
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

        <Grid container justify="space-around">
          <Card>
            <CardContent>
              <Typography variant="headline" component="h2">
                Total Word Count: {this.state.totalWordCount}
              </Typography>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Word</TableCell>
                    <TableCell numeric>Count</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.counts.map(n => {
                    return (
                      <TableRow key={n.word}>
                        <TableCell component="th" scope="row">{n.word}</TableCell>
                        <TableCell numeric>{n.count}</TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </Grid>
      </div>
    );
  }
}
export default WordCount;
