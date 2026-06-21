/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#1e3a5f',
          light: '#2d5986',
          dark: '#152a45',
        },
        accent: {
          DEFAULT: '#e8a838',
          light: '#f0c060',
        },
      },
    },
  },
  plugins: [],
}
