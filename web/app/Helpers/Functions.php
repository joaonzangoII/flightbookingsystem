<?php

function rename_file($filename, $mime)
{
    $filename = preg_replace('/\\.[^.\\s]{3,4}$/', '', $filename);
    $filename = str_slug($filename, '-');
    $filename = '/' . $filename . '_' . str_random(32) . '.' . $mime;

    return $filename;
}

function uploadImage($file, $image, String $path = "/uploads/images")
{
  if ($image){
    $fileName = rename_file($file->getClientOriginalName(), $file->getClientOriginalExtension());
    $public_path = public_path() . $path;
    $file->move($public_path, $fileName);
    return $path . $fileName;
  }

  return null;
}

function randImg($path)
{
  $public_path = public_path() . $path;
  $images = glob($public_path . '*.{jpg,jpeg,png,gif}', GLOB_BRACE);
  $randomImage = $images[array_rand($images)];
  $randomImage = str_replace('/media/windows/Projects/clients/flight_booking_system/web/public',
                             '',
                             $randomImage);
  return $randomImage;
}
