## Copyright (C) 2023 geano
##
## This program is free software: you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
##
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see <https://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {} {@var{retval} =} f1 (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: geano <geano@DESKTOP-T64O1IM>
## Created: 2023-10-09

function y = f1 (x) #the function name must be the same with the file name
  y=x^2-x;          #the function file must be in the current work directory, otherwise the program might not find it
endfunction
