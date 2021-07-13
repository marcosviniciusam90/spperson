FROM nginx:latest

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

# Copy the build output to replace the default nginx contents.
COPY dist/frontend /usr/share/nginx/html

# Expose port 80
EXPOSE 80