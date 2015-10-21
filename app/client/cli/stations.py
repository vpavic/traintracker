#!/usr/bin/env python3

# command line interface for http://voznired.hocuinternet.com/
#
# uses stations REST endpoint configured in config.yml file

import argparse
import yaml
import requests
from array import array


def parse_args():
    parser = argparse.ArgumentParser(description='Live train info command line client.')
    parser.add_argument('train_no', help='train number')
    parser.add_argument('-a', '--all', help='show all stations', action='store_true')
    parser.add_argument('-u', '--url', help='stations URL')
    return parser.parse_args()


def load_config():
    try:
        with open('config.yml', 'r') as file:
            config = yaml.load(file)

            if isinstance(config, dict):
                return config
            else:
                print_error('Invalid configuration')
    except FileNotFoundError:
        print_error('Configuration file not found')


def load_data(url):
    try:
        r = requests.get(url)
    except requests.exceptions.RequestException:
        print_error('Error executing request towards \'' + url + '\'')
    return r.json()


def print_stations(data, print_all=False):
    if 'stations' in data and len(data.get('stations')) > 0:
        stations = data.get('stations')

        column_widths = array('i')
        column_widths.append(max([len(station.get('name')) for station in stations]))
        column_widths.append(max([len(station.get('arrivalTime', ' ' * 5)) for station in stations]))
        column_widths.append(max([len(str(station.get('arrivalDelay', ' '))) for station in stations]))
        column_widths.append(max([len(station.get('departureTime', ' ' * 5)) for station in stations]))
        column_widths.append(max([len(str(station.get('departureDelay', ' '))) for station in stations]))

        print(' ', '-' * (sum(column_widths) + 4))
        print(' ', 'station'.center(column_widths[0]), 'arr'.center(column_widths[1] + column_widths[2] + 1),
              'dep'.center(column_widths[3] + column_widths[4] + 1))
        print(' ', '-' * (sum(column_widths) + 4))

        for station in stations if print_all else stations[-1:]:
            name = station.get('name').ljust(column_widths[0])
            arrival_time = station.get('arrivalTime', ' ' * 5).rjust(column_widths[1])
            arrival_delay = str(station.get('arrivalDelay', ' ')).rjust(column_widths[2])
            departure_time = station.get('departureTime', ' ' * 5).rjust(column_widths[3])
            departure_delay = str(station.get('departureDelay', ' ')).rjust(column_widths[4])

            print(' ', name, arrival_time, arrival_delay, departure_time, departure_delay)

        print(' ', '-' * (sum(column_widths) + 4))
    else:
        print('No data found')


def print_error(message):
    print(message)
    exit(1)


def main():
    args = parse_args()

    if args.url:
        url = args.url
    else:
        config = load_config()

        try:
            url = config['url']
        except KeyError:
            print_error('Remote URL not configured')

    try:
        formatted_url = url.format(args.train_no)
    except AttributeError:
        print_error('Invalid remote URL in configuration')

    print('Requesting live info for train {}...'.format(args.train_no))

    data = load_data(formatted_url)
    print_stations(data, args.all)


if __name__ == "__main__":
    main()
